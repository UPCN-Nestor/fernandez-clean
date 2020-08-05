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
    
    @Query(value="select * from v_suministros v where trim(v.socio) = :socio",  nativeQuery=true)
    List<Object[]> findEnVistaPorSocio(@Param("socio") String socio);

    @Query(value="select 0 as id, suministro, servicio, inmueble, usuario, dni, tarifa from v_suministros v where trim(v.suministro) = :sumi",  nativeQuery=true)
    List<Object[]> findEnVistaPorSuministro(@Param("sumi") String sumi);

    @Query(value = "select s.* from Suministro s left join suministro_user su on s.id = su.suministro_id where su.user_id = :userid", nativeQuery = true)
    List<Suministro> findAllByUserid(@Param("userid") Long userid);

    @Query(value = "select count(*) from v_suministros v where trim(v.suministro) = :sumi and trim(v.dni) = :dni", nativeQuery = true)
    Integer coincideNumeroConDNI(@Param("sumi") String sumi, @Param("dni") String dni);
}
