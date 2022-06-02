package com.gfa.orientationexamsecondtime.repositories;

import com.gfa.orientationexamsecondtime.models.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass,Long> {
    boolean existsByName(String name);
    StudentClass findByName(String name);
}
