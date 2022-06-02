package com.gfa.orientationexamsecondtime.controllers;

import com.gfa.orientationexamsecondtime.models.Mentor;
import com.gfa.orientationexamsecondtime.models.NewDataToMentor;
import com.gfa.orientationexamsecondtime.services.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    MentorService mentorService;

    public MainController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("list", mentorService.findAll());
        return "index";
    }

    @PostMapping("/mentor")
    public String saveMentor(@RequestParam String name,
                             @RequestParam String className) {
        return mentorService.saveToDB(name, className);
    }

    @GetMapping("/mentor/{id}")
    public String getDetails(@PathVariable int id, Model model) {
        Mentor mentor = mentorService.findAll().get(id - 1);
        model.addAttribute("name", mentor.getName());
        model.addAttribute("className", mentor.getStudentClass().getName());
        return "details";
    }

    @GetMapping("api/mentors")
    public ResponseEntity getMentors(@RequestParam String className) {
        return mentorService.getMentors(className);
    }

    @PutMapping("api/mentors/{mentorId}")
    public ResponseEntity updateMentor(@PathVariable Integer mentorId,
                                       @RequestBody NewDataToMentor newDataToMentor){
        mentorId-=1;
        if (newDataToMentor.getClassName()==null){
            return ResponseEntity.status(400).body("Not valid className");
        } else if (mentorId>mentorService.findAll().size()||mentorId<0) {
            return ResponseEntity.status(404).body("Not valid mentorId");
        }
        return mentorService.updateMentor(mentorId, newDataToMentor);
    }
}
