package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String code;

    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "manager")
    @JsonIgnoreProperties(value = {"companies", "regions", "branches", "departments", "employees"}, allowSetters = true)
    private Person manager;

    @ManyToOne
    @JoinColumn(name = "company")
    @JsonIgnoreProperties(value = {"regions"}, allowSetters = true)
    private Company company;

    @OneToMany(mappedBy = "region")
    @JsonIgnoreProperties(value = {"region"}, allowSetters = true)
    private List<Branch> branches = new ArrayList<>();

    @JsonCreator
    public static Region Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Region branch = mapper.readValue(jsonString, Region.class);
        return branch;
    }
}
