package com.yupi.ecsa.timesheet.service.impl;

import com.yupi.ecsa.timesheet.security.SecurityUtils;
import com.yupi.ecsa.timesheet.service.ProjectService;
import com.yupi.ecsa.timesheet.service.UserService;
import com.yupi.ecsa.timesheet.domain.Project;
import com.yupi.ecsa.timesheet.domain.User;
import com.yupi.ecsa.timesheet.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    @Override
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        if (project.getId() != null) {
            // Es un update
            Optional<Project> opCurrentProject = projectRepository.findById(project.getId());
            if (opCurrentProject.isPresent()) {
                Project currentProject = opCurrentProject.get();
                project.setOdooId(currentProject.getOdooId());
                project.setActive(currentProject.isActive());
                if(project.getPartner() == null){
                    project.setPartner(currentProject.getPartner());
                }
                if (project.getName() == null) {
                    project.setName(currentProject.getName());
                }

                if (project.getPartner() == null) {
                    currentProject.setPartner(project.getPartner());
                }

                if (project.getTasks() == null) {
                    project.setTasks(currentProject.getTasks());
                } else {
                    project.getTasks().addAll(currentProject.getTasks());
                }

                if (project.getUsers() == null) {
                    project.setUsers(currentProject.getUsers());
                } else {
                    project.getUsers().addAll(currentProject.getUsers());
                }
            }

        }
        project.getTasks().forEach(task -> task.setProject(project));
        project.getUsers().forEach(user -> user.getProjects().add(project));
        return projectRepository.save(project);
    }

    /**
     * Get all the projects by user
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        log.debug("Request to get all Projects");
//        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.getCurrentUser();
        return projectRepository.findAllWithEagerRelationships(user.get().getId());
    }

    /**
     * Get all the Project with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Project> findAllWithEagerRelationships(Pageable pageable) {

        return projectRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
}
