package org.tbk.sbswdemo.model.projection;

import org.springframework.data.rest.core.config.Projection;
import org.tbk.sbswdemo.model.Model;
import org.tbk.sbswdemo.model.Series;

@Projection(name = "withSeries", types = {Model.class})
public interface ModelWithSeries {
    String getName();

    String getDescription();

    Series getSeries();
}
