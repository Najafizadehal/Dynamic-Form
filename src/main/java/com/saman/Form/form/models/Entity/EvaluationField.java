package com.saman.Form.form.models.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EvaluationField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int score;

    @ManyToOne
    @JoinColumn(name = "evaluation_criteria_id")
    private EvaluationCriteria evaluationCriteria;

    public EvaluationField(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
