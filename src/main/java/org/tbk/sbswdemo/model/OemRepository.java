package org.tbk.sbswdemo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
@RepositoryRestResource(collectionResourceRel = "oem", path = "oem")
public interface OemRepository extends JpaRepository<Oem, Long> {

    Page<Oem> findByName(@Param("name") String name, Pageable page);
}
