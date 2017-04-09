package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class OperationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @JsonCreator
    public static OperationType Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OperationType operationTo = mapper.readValue(jsonString, OperationType.class);
        return operationTo;
    }
}
