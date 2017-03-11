package org.tbk.sbswdemo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "model", path = "model")
public interface ModelRepository extends JpaRepository<Model, Long> {

    Page<Model> findByName(@Param("name") String name, Pageable pageable);

    Page<Model> findBySeries(Series series, Pageable pageable);
}
