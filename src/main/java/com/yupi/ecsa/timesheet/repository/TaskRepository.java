package com.yupi.ecsa.timesheet.repository;

import com.yupi.ecsa.timesheet.domain.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select task from Task task where task.user.login = ?#{principal.username}")
    List<Task> findByUserIsCurrentUser();

    @Query("select task from Task task where task.project.id = :projectId and task.user.login = ?#{principal.username}")
    Optional<List<Task>> findByProjectAndUserIsCurrentUser(@Param("projectId") Long projectId);

}
