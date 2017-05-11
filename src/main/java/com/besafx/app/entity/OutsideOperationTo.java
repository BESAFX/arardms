package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class OutsideOperationTo implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "outsideOperationToSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "OUTSIDE_OPERATION_TO_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "outsideOperationToSequenceGenerator")
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
