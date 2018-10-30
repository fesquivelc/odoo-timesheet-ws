package com.yupi.ecsa.timesheet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yupi.ecsa.timesheet.domain.Project;
import com.yupi.ecsa.timesheet.domain.Timesheet;
import com.yupi.ecsa.timesheet.service.ProjectService;
import com.yupi.ecsa.timesheet.service.TimesheetService;
import com.yupi.ecsa.timesheet.web.rest.errors.BadRequestAlertException;
import com.yupi.ecsa.timesheet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "project";

    private final ProjectService projectService;

    private final TimesheetService timesheetService;

    public ProjectResource(ProjectService projectService, TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
        this.projectService = projectService;
    }

    /**
     * POST /projects : Create a new project.
     *
     * @param project the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         project, or with status 400 (Bad Request) if the project has already
     *         an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    @Timed
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            throw new BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /projects : Updates an existing project.
     *
     * @param project the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         project, or with status 400 (Bad Request) if the project is not
     *         valid, or with status 500 (Internal Server Error) if the project
     *         couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    @Timed
    public ResponseEntity<Project> updateProject(@RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, project.getId().toString()))
                .body(result);
    }

    @PutMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Project> updateProjectId(@PathVariable Long id, @RequestBody Project project)
            throws URISyntaxException {
        log.debug("REST request to update Project with id: {}", id);
        Project result = projectService.save(project);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, project.getId().toString()))
                .body(result);
    }

    /**
     * GET /projects : get all the projects.
     *
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of projects in
     *         body
     */
    @GetMapping("/projects")
    @Timed
    public List<Project> getAllProjects(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Projects");
        return projectService.findAll();
    }

//    @PatchMapping("/projects/{id}")
//    @Timed
//    public ResponseEntity<?> patchProjects(@RequestBody Map<String, Object> projectUpdate, @PathVariable("id") String id){
//        projectService.save()
//    }

    /**
     * GET /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or
     *         with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Optional<Project> project = projectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(project);
    }

    @GetMapping("/projects/{id}/timesheets")
    @Timed
    public ResponseEntity<List<Timesheet>> getTimesheet(@PathVariable Long id) {
        log.debug("REST request to get timesheets for project : {}", id);
        Optional<List<Timesheet>> timesheets = timesheetService.findByProjectAndCurrentUser(id);
        return ResponseUtil.wrapOrNotFound(timesheets);
    }

    /**
     * DELETE /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
