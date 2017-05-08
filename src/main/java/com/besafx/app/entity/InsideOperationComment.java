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
public class InsideOperationComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer code;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fromPerson")
    private Person fromPerson;

    @ManyToOne
    @JoinColumn(name = "insideOperation")
    private InsideOperation insideOperation;

    @JsonCreator
    public static InsideOperationComment Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InsideOperationComment operationTo = mapper.readValue(jsonString, InsideOperationComment.class);
        return operationTo;
    }
}
