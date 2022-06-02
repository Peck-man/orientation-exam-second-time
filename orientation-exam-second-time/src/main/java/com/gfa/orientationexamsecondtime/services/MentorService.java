package com.gfa.orientationexamsecondtime.services;

import com.gfa.orientationexamsecondtime.models.Mentor;
import com.gfa.orientationexamsecondtime.models.MentorDTO;
import com.gfa.orientationexamsecondtime.models.NewDataToMentor;
import com.gfa.orientationexamsecondtime.models.StudentClass;
import com.gfa.orientationexamsecondtime.repositories.MentorsRepository;
import com.gfa.orientationexamsecondtime.repositories.StudentClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentorService {
    MentorsRepository mentorsRepository;
    StudentClassRepository studentClassRepository;

    public MentorService(MentorsRepository mentorsRepository, StudentClassRepository studentClassRepository) {
        this.mentorsRepository = mentorsRepository;
        this.studentClassRepository = studentClassRepository;
    }

    public List<Mentor> findAll() {
        return mentorsRepository.findAll();
    }

    public String saveToDB(String name, String className) {
        StudentClass studentClass;
        if (mentorsRepository.existsByName(name)) {
            return "alreadyExists";
        }
        if (studentClassRepository.existsByName(className)) {
            studentClass = studentClassRepository.findByName(className);
        } else {
            studentClass = new StudentClass(className);
            studentClassRepository.save(studentClass);
        }
        Mentor mentor = new Mentor(name, studentClass);
        mentorsRepository.save(mentor);
        return "redirect:/mentor/" + mentor.getId();
    }

    public boolean isPresent(Long id){
       return mentorsRepository.existsById(id);
    }

    public ResponseEntity getMentors(String className) {
        if (studentClassRepository.existsByName(className)) {
            List<MentorDTO> list = new ArrayList<>();
            for (Mentor m : mentorsRepository.findByStudentClass_Name(className)) {
                list.add(new MentorDTO(m.getName()));
            }
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(400).body("We dont have this class");
    }
    public ResponseEntity updateMentor(Long id, NewDataToMentor newDataToMentor){

        String newName = newDataToMentor.getName();
        StudentClass newClass = findClass(newDataToMentor.getClassName());
        Mentor mentor = findAll().stream().filter(mentor1 -> mentor1.getId()==id).findFirst().get();
        mentor.setName(newName);
        mentor.setStudentClass(newClass);
        mentorsRepository.save(mentor);
        return ResponseEntity.ok(mentor);
    }

    public StudentClass findClass(String className){
        if (studentClassRepository.existsByName(className)){
            return studentClassRepository.findByName(className);
        }
        StudentClass studentClass = new StudentClass(className);
        studentClassRepository.save(studentClass);
        return studentClass;
    }
}
