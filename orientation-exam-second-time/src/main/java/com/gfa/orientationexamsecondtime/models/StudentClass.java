package com.gfa.orientationexamsecondtime.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<Mentor> mentorList;
    public StudentClass() {
    }

    public StudentClass(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mentor> getMentorList() {
        return mentorList;
    }

    public void setMentorList(List<Mentor> mentorList) {
        this.mentorList = mentorList;
    }
}
