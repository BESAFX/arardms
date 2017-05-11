package com.besafx.app.entity;
import com.besafx.app.entity.enums.DeliveryWay;
import com.besafx.app.entity.enums.Direction;
import com.besafx.app.entity.enums.Importance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class OutsideOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "outsideOperationSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "OUTSIDE_OPERATION_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "outsideOperationSequenceGenerator")
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
    private Direction direction;

    private Boolean locked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

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

    @ManyToOne
    @JoinColumn(name = "branch")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    @JsonCreator
    public static OutsideOperation Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OutsideOperation operation = mapper.readValue(jsonString, OutsideOperation.class);
        return operation;
    }
}
