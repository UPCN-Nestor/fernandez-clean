package com.ofv.ofv.repository;

import com.ofv.ofv.domain.Suministro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Suministro entity.
 */
@Repository
public interface SuministroRepository extends JpaRepository<Suministro, Long> {

    @Query(value = "select distinct suministro from Suministro suministro left join fetch suministro.users",
        countQuery = "select count(distinct suministro) from Suministro suministro")
    Page<Suministro> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct suministro from Suministro suministro left join fetch suministro.users")
    List<Suministro> findAllWithEagerRelationships();

    @Query("select suministro from Suministro suministro left join fetch suministro.users where suministro.id =:id")
    Optional<Suministro> findOneWithEagerRelationships(@Param("id") Long id);
}
