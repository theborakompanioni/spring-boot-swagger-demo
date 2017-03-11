package org.tbk.sbswdemo.model.projection;

import org.springframework.data.rest.core.config.Projection;
import org.tbk.sbswdemo.model.Series;
import org.tbk.sbswdemo.model.User;

@Projection(name = "withOem", types = {Series.class})
public interface SeriesWithOemProjection {
    String getName();

    String getDescription();

    User getOwner();
}
