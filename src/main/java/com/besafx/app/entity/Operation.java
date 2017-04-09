package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer code;

    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    private Boolean locked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @NotNull
    private Integer fromId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IdType fromType;

    @NotNull
    private Integer toId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IdType toType;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @JsonCreator
    public static Operation Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Operation operation = mapper.readValue(jsonString, Operation.class);
        return operation;
    }

    public enum IdType{
        Company, Region, Branch, Department, Person
    }
}
