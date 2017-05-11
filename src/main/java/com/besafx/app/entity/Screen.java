package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
public class Screen implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "screenSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "SCREEN_SEQUENCE"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "screenSequenceGenerator")
    private Long id;

    private String code;

    private String name;

    @JsonCreator
    public static Screen Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Screen screen = mapper.readValue(jsonString, Screen.class);
        return screen;
    }
}
