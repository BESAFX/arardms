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
public class OutsideOperationTo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @ManyToOne
    @JoinColumn(name = "fromPerson")
    private Person fromPerson;

    @ManyToOne
    @JoinColumn(name = "toPerson")
    private Person toPerson;

    @ManyToOne
    @JoinColumn(name = "outsideOperation")
    private OutsideOperation outsideOperation;

    @JsonCreator
    public static OutsideOperationTo Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OutsideOperationTo operationTo = mapper.readValue(jsonString, OutsideOperationTo.class);
        return operationTo;
    }
}
