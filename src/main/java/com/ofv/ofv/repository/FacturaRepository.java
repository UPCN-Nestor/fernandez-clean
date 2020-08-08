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

    @Query(value = "select min(id) id, suministro, usuario, inmueble, periodo, numero, vencimiento_1, vencimiento_2, importe_1, importe_2, servicio, tarifa, archivopdf, estado, dni, socio, min(archivo_facturas_id) archivo_facturas_id from Factura f where trim(f.suministro) = :sumi group by  suministro, usuario, inmueble, periodo, numero, vencimiento_1, vencimiento_2, importe_1, importe_2, servicio, tarifa, archivopdf, estado, dni, socio", nativeQuery = true)
    List<Factura> findAllBySuministro(@Param("sumi") String sumi);

    Factura findByNumero(@Param("numero") String numero);

    List<Factura> findAllByArchivoFacturasId(@Param("archivo_facturas_id") Long archivoFacturasId);
}
