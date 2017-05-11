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

@Data
@Entity
public class OperationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "operationToSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "OPERATION_TO_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "operationToSequenceGenerator")
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
