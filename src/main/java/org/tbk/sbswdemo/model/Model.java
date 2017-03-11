package org.tbk.sbswdemo.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "model")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.CHAR, columnDefinition = "default 'I'")
@DiscriminatorValue("I")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "model_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String description;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    protected Model() {
    }

    public Model(String name, Series series) {
        this.name = name;
        this.series = series;
    }

    @Override
    public String toString() {
        return String.format(
                "Model[id=%d, name='%s']",
                id, name);
    }
}
