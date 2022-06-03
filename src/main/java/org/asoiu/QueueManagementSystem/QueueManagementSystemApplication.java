package org.asoiu.QueueManagementSystem;

import org.asoiu.QueueManagementSystem.entity.Student;
import org.asoiu.QueueManagementSystem.repository.StudentRepository;
import org.asoiu.QueueManagementSystem.util.SearchCriteria;
import org.asoiu.QueueManagementSystem.util.StudentSpecifications;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@SpringBootApplication
public class QueueManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueManagementSystemApplication.class, args);



	}

}
