package org.tbk.sbswdemo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "user", path = "user")
//@RepositoryRestResource(excerptProjection = UserWithListsProjection.class, collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByName(@Param("name") String name, Pageable page);
}
