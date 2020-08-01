package com.ofv.ofv.repository;

import java.util.List;

import com.ofv.ofv.domain.Factura;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Factura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query(value = "select f.* from Factura f where trim(f.suministro) = :sumi", nativeQuery = true)
    List<Factura> findAllBySuministro(@Param("sumi") String sumi);

}
