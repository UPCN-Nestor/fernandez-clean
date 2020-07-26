package com.ofv.ofv.repository;

import com.ofv.ofv.domain.ArchivoFacturas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArchivoFacturas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivoFacturasRepository extends JpaRepository<ArchivoFacturas, Long> {
}
