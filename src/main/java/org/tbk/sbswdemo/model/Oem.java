package org.tbk.sbswdemo.model;

import com.google.common.collect.ImmutableList;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Data
@Entity
@Table(name = "oem")
public class Oem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oem_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String description;

    @OneToMany(mappedBy = "oem", cascade = CascadeType.ALL)
    private List<Series> series;

    protected Oem() {
    }

    public Oem(String name, String description) {
        this.name = requireNonNull(name);
        this.description = description;
    }


    public List<Series> getSeries() {
        return ImmutableList.copyOf(series);
    }

    public void setSeries(List<Series> series) {
        this.series = series == null ? Collections.emptyList() : ImmutableList.copyOf(series);
    }

    @Override
    public String toString() {
        return String.format(
                "Oem[id=%d, name='%s', code=%s]",
                id, name, code);
    }
}
