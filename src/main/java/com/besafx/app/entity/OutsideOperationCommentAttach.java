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
public class OutsideOperationCommentAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "outsideOperationCommentAttachSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "OUTSIDE_OPERATION_COMMENT_ATTACH_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "outsideOperationCommentAttachSequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String link;

    @ManyToOne
    @JoinColumn(name = "outsideOperationComment")
    private OutsideOperationComment outsideOperationComment;

    @JsonCreator
    public static OutsideOperationCommentAttach Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OutsideOperationCommentAttach operationTo = mapper.readValue(jsonString, OutsideOperationCommentAttach.class);
        return operationTo;
    }
}
