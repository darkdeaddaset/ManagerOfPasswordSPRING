package com.savin.mop.repository;

import com.savin.mop.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> getDepartmentByNameAndPassword(String name, String password);
    Optional<Department> getDepartmentByName(String name);
}
