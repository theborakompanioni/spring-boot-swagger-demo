package org.tbk.sbswdemo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "series", path = "series")
public interface SeriesRepository extends JpaRepository<Series, Long> {

    Page<Series> findByName(@Param("name") String name, Pageable pageable);

    Page<Series> findByModels(Model models, Pageable pageable);

    Page<Series> findByOem(Oem oem, Pageable pageable);
}
