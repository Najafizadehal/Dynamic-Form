package com.saman.Form.form.models.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationCriteria> criteria = new ArrayList<>();

    public Evaluation(String name) {
        this.name = name;
    }

    public void addCriteria(EvaluationCriteria criteria) {
        criteria.setEvaluation(this);
        this.criteria.add(criteria);
    }
}
