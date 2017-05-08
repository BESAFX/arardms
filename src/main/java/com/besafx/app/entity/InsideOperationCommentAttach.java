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
public class InsideOperationCommentAttach implements Serializable {

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
    @JoinColumn(name = "insideOperationComment")
    private InsideOperationComment insideOperationComment;

    @JsonCreator
    public static InsideOperationCommentAttach Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InsideOperationCommentAttach operationTo = mapper.readValue(jsonString, InsideOperationCommentAttach.class);
        return operationTo;
    }
}
