package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
public class InsideOperationAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String link;

    @ManyToOne
    @JoinColumn(name = "insideOperation")
    private InsideOperation insideOperation;

    @JsonCreator
    public static InsideOperationAttach Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InsideOperationAttach operationTo = mapper.readValue(jsonString, InsideOperationAttach.class);
        return operationTo;
    }
}
