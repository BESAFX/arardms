package com.besafx.app.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "personSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PERSON_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "personSequenceGenerator")
    @JsonView(Views.Summery.class)
    private Long id;

    @JsonView(Views.Summery.class)
    private String name;

    @JsonView(Views.Summery.class)
    private String nickname;

    private String address;

    private String mobile;

    private String nationality;

    private String identityNumber;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String photo;

    private String qualification;

    @JsonView(Views.Summery.class)
    private String email;

    private String password;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean technicalSupport;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean tokenExpired;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean active;

    @JsonIgnore
    private String hiddenPassword;

    private Date lastLoginDate;

    private String lastLoginLocation;

    private String ipAddress;

    private String hostName;

    @ManyToOne
    @JoinColumn(name = "Team")
    @JsonIgnoreProperties(value = {"persons"}, allowSetters = true)
    private Team team;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Company> companies = new ArrayList<>();

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Branch> branches = new ArrayList<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @JsonCreator
    public static Person Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(jsonString, Person.class);
        return person;
    }
}
