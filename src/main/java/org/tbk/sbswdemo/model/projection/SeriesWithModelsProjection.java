package org.tbk.sbswdemo.model.projection;

import org.springframework.data.rest.core.config.Projection;
import org.tbk.sbswdemo.model.Model;
import org.tbk.sbswdemo.model.Series;

import java.util.List;

@Projection(name = "withModels", types = {Series.class})
public interface SeriesWithModelsProjection {
    String getName();

    String getDescription();

    List<Model> getModels();
}
