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
public class OutsideOperationComment implements Serializable {

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
    @JoinColumn(name = "outsideOperation")
    private OutsideOperation outsideOperation;

    @JsonCreator
    public static OutsideOperationComment Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OutsideOperationComment operationTo = mapper.readValue(jsonString, OutsideOperationComment.class);
        return operationTo;
    }
}
