package com.yupi.ecsa.timesheet.repository;

import com.yupi.ecsa.timesheet.domain.Timesheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Timesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    @Query("select timesheet from Timesheet timesheet where timesheet.user.login = ?#{principal.username}")
    List<Timesheet> findByUserIsCurrentUser();

}
