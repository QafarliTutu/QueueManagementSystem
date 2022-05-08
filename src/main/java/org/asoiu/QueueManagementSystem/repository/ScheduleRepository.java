package org.asoiu.QueueManagementSystem.repository;

import org.asoiu.QueueManagementSystem.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByEvent(Long event);
}
