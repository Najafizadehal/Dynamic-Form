package com.saman.Form.form.models.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class EvaluationCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "evaluationCriteria")
    private List<EvaluationField> fields = new ArrayList<>();

    public EvaluationCriteria(String name) {
        this.name = name;
    }

    public void addField(EvaluationField field) {
        field.setEvaluationCriteria(this);
        this.fields.add(field);
    }
}
