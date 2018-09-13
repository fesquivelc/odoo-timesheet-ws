package com.yupi.ecsa.timesheet.service.impl;

import com.yupi.ecsa.timesheet.service.TimesheetService;
import com.yupi.ecsa.timesheet.domain.Timesheet;
import com.yupi.ecsa.timesheet.repository.TimesheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Timesheet.
 */
@Service
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

    private final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

    private final TimesheetRepository timesheetRepository;

    public TimesheetServiceImpl(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    /**
     * Save a timesheet.
     *
     * @param timesheet the entity to save
     * @return the persisted entity
     */
    @Override
    public Timesheet save(Timesheet timesheet) {
        log.debug("Request to save Timesheet : {}", timesheet);        return timesheetRepository.save(timesheet);
    }

    /**
     * Get all the timesheets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Timesheet> findAll() {
        log.debug("Request to get all Timesheets");
        return timesheetRepository.findAll();
    }


    /**
     * Get one timesheet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Timesheet> findOne(Long id) {
        log.debug("Request to get Timesheet : {}", id);
        return timesheetRepository.findById(id);
    }

    /**
     * Delete the timesheet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Timesheet : {}", id);
        timesheetRepository.deleteById(id);
    }
}
