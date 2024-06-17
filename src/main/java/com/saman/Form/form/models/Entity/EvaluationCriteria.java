package com.saman.Form.form.models.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class EvaluationCriteria {

    private long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationField> fields = new ArrayList<>();

    public EvaluationCriteria() {}

    public EvaluationCriteria(String name) {
        this.name = name;
    }

    public void addField(EvaluationField field) {
        fields.add(field);
    }
}
