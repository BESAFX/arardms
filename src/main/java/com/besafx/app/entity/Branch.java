package com.besafx.app.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(Views.Summery.class)
    private Long id;

    @JsonView(Views.Summery.class)
    private String code;

    @JsonView(Views.Summery.class)
    private String name;

    @JsonView(Views.Summery.class)
    private String address;

    @JsonView(Views.Summery.class)
    private String phone;

    @JsonView(Views.Summery.class)
    private String mobile;

    @JsonView(Views.Summery.class)
    private String fax;

    @JsonView(Views.Summery.class)
    private String email;

    @JsonView(Views.Summery.class)
    private String website;

    @JsonView(Views.Summery.class)
    private String commericalRegisteration;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @JsonView(Views.Summery.class)
    private String logo;

    @ManyToOne
    @JoinColumn(name = "company")
    @JsonIgnoreProperties(value = {"manager", "branches"}, allowSetters = true)
    @JsonView(Views.Summery.class)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "manager")
    @JsonIgnoreProperties(value = {"branch", "companies", "branches"}, allowSetters = true)
    @JsonView(Views.Summery.class)
    private Person manager;

    @JsonCreator
    public static Branch Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Branch branch = mapper.readValue(jsonString, Branch.class);
        return branch;
    }
}
