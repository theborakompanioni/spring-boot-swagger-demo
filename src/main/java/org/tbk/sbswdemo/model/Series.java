package org.tbk.sbswdemo.model;

import com.google.common.collect.ImmutableList;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "series_id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String description;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<Model> models = Collections.emptyList();

    @ManyToOne
    @JoinColumn(name = "oem_id")
    private Oem oem;

    protected Series() {
    }

    public Series(String name, Oem oem) {
        this.name = name;
        this.oem = oem;
    }

    @Override
    public String toString() {
        return String.format(
                "Series[id=%d, name='%s']",
                id, name);
    }

    public List<Model> getModels() {
        return ImmutableList.copyOf(models);
    }

    public void setModels(List<Model> models) {
        this.models = models == null ? Collections.emptyList() : ImmutableList.copyOf(models);
    }
}
