package com.saman.Form.form.models.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class EvaluationField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int score;

    public EvaluationField() {}

    public EvaluationField(String name, int score) {
        this.name = name;
        this.score = score;
    }

}
