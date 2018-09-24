package com.yupi.ecsa.timesheet.service.impl;

import com.yupi.ecsa.timesheet.service.ProjectService;
import com.yupi.ecsa.timesheet.domain.Project;
import com.yupi.ecsa.timesheet.domain.Task;
import com.yupi.ecsa.timesheet.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
        project.getTasks().forEach(task -> task.setProject(project));
        return projectRepository.save(project);
    }

    /**
     * Get all the projects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        log.debug("Request to get all Projects");
        return projectRepository.findAllWithEagerRelationships();
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
