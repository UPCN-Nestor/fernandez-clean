package com.ofv.ofv.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ofv.ofv.web.rest.TestUtil;

public class ArchivoFacturasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArchivoFacturas.class);
        ArchivoFacturas archivoFacturas1 = new ArchivoFacturas();
        archivoFacturas1.setId(1L);
        ArchivoFacturas archivoFacturas2 = new ArchivoFacturas();
        archivoFacturas2.setId(archivoFacturas1.getId());
        assertThat(archivoFacturas1).isEqualTo(archivoFacturas2);
        archivoFacturas2.setId(2L);
        assertThat(archivoFacturas1).isNotEqualTo(archivoFacturas2);
        archivoFacturas1.setId(null);
        assertThat(archivoFacturas1).isNotEqualTo(archivoFacturas2);
    }
}
