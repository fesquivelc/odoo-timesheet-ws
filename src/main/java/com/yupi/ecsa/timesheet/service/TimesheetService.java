package com.yupi.ecsa.timesheet.service;

import com.yupi.ecsa.timesheet.domain.Timesheet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Timesheet.
 */
public interface TimesheetService {

    /**
     * Save a timesheet.
     *
     * @param timesheet the entity to save
     * @return the persisted entity
     */
    Timesheet save(Timesheet timesheet);

    /**
     * Get all the timesheets.
     *
     * @return the list of entities
     */
    List<Timesheet> findAll();


    /**
     * Get the "id" timesheet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Timesheet> findOne(Long id);

    /**
     * Delete the "id" timesheet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<List<Timesheet>> findByProjectAndCurrentUser(Long projectId);
}
