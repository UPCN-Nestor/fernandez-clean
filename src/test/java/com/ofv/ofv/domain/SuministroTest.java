package com.ofv.ofv.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ofv.ofv.web.rest.TestUtil;

public class SuministroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suministro.class);
        Suministro suministro1 = new Suministro();
        suministro1.setId(1L);
        Suministro suministro2 = new Suministro();
        suministro2.setId(suministro1.getId());
        assertThat(suministro1).isEqualTo(suministro2);
        suministro2.setId(2L);
        assertThat(suministro1).isNotEqualTo(suministro2);
        suministro1.setId(null);
        assertThat(suministro1).isNotEqualTo(suministro2);
    }
}
