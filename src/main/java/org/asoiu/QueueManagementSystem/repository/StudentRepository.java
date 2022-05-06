package org.asoiu.QueueManagementSystem.repository;

import org.asoiu.QueueManagementSystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
