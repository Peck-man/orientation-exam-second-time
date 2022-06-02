package com.gfa.orientationexamsecondtime.repositories;

import com.gfa.orientationexamsecondtime.models.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorsRepository extends JpaRepository<Mentor, Long> {
    boolean existsByName(String name);
    List<Mentor> findByStudentClass_Name(String className);
}
