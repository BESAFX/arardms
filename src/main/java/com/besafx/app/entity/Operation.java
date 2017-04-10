package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String deliveryManFrom;

    private String deliveryManTo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DeliveryWay deliveryWay;

    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Importance importance;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Structure structure;

    private Boolean locked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @NotNull
    private Long fromId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IdType fromType;

    @NotNull
    private Long toId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IdType toType;

    @ManyToOne
    @JoinColumn(name = "person")
    @JsonIgnoreProperties(value = {"companies", "regions", "branches", "departments", "employees"}, allowSetters = true)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "operationType")
    private OperationType operationType;

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

    public enum DeliveryWay{
        Hand, Email, Fax, Post
    }

    public enum Importance{
       Regular, Important, Critical
    }

    public enum Structure{
        Incoming, Outgoing
    }
}
