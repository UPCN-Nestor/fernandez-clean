package com.ofv.ofv.web.rest;

import com.ofv.ofv.OfVApp;
import com.ofv.ofv.domain.Factura;
import com.ofv.ofv.repository.FacturaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FacturaResource} REST controller.
 */
@SpringBootTest(classes = OfVApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FacturaResourceIT {

    private static final String DEFAULT_SUMINISTRO = "AAAAAAAAAA";
    private static final String UPDATED_SUMINISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_INMUEBLE = "AAAAAAAAAA";
    private static final String UPDATED_INMUEBLE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VENCIMIENTO_1 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMIENTO_1 = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VENCIMIENTO_2 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMIENTO_2 = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_IMPORTE_1 = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE_1 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IMPORTE_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE_2 = new BigDecimal(2);

    private static final String DEFAULT_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_TARIFA = "AAAAAAAAAA";
    private static final String UPDATED_TARIFA = "BBBBBBBBBB";

    private static final String DEFAULT_ARCHIVOPDF = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVOPDF = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIO = "AAAAAAAAAA";
    private static final String UPDATED_SOCIO = "BBBBBBBBBB";

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacturaMockMvc;

    private Factura factura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createEntity(EntityManager em) {
        Factura factura = new Factura()
            .suministro(DEFAULT_SUMINISTRO)
            .usuario(DEFAULT_USUARIO)
            .inmueble(DEFAULT_INMUEBLE)
            .periodo(DEFAULT_PERIODO)
            .numero(DEFAULT_NUMERO)
            .vencimiento1(DEFAULT_VENCIMIENTO_1)
            .vencimiento2(DEFAULT_VENCIMIENTO_2)
            .importe1(DEFAULT_IMPORTE_1)
            .importe2(DEFAULT_IMPORTE_2)
            .servicio(DEFAULT_SERVICIO)
            .tarifa(DEFAULT_TARIFA)
            .archivopdf(DEFAULT_ARCHIVOPDF)
            .estado(DEFAULT_ESTADO)
            .dni(DEFAULT_DNI)
            .socio(DEFAULT_SOCIO);
        return factura;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createUpdatedEntity(EntityManager em) {
        Factura factura = new Factura()
            .suministro(UPDATED_SUMINISTRO)
            .usuario(UPDATED_USUARIO)
            .inmueble(UPDATED_INMUEBLE)
            .periodo(UPDATED_PERIODO)
            .numero(UPDATED_NUMERO)
            .vencimiento1(UPDATED_VENCIMIENTO_1)
            .vencimiento2(UPDATED_VENCIMIENTO_2)
            .importe1(UPDATED_IMPORTE_1)
            .importe2(UPDATED_IMPORTE_2)
            .servicio(UPDATED_SERVICIO)
            .tarifa(UPDATED_TARIFA)
            .archivopdf(UPDATED_ARCHIVOPDF)
            .estado(UPDATED_ESTADO)
            .dni(UPDATED_DNI)
            .socio(UPDATED_SOCIO);
        return factura;
    }

    @BeforeEach
    public void initTest() {
        factura = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactura() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();

        // Create the Factura
        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isCreated());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate + 1);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getSuministro()).isEqualTo(DEFAULT_SUMINISTRO);
        assertThat(testFactura.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testFactura.getInmueble()).isEqualTo(DEFAULT_INMUEBLE);
        assertThat(testFactura.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testFactura.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFactura.getVencimiento1()).isEqualTo(DEFAULT_VENCIMIENTO_1);
        assertThat(testFactura.getVencimiento2()).isEqualTo(DEFAULT_VENCIMIENTO_2);
        assertThat(testFactura.getImporte1()).isEqualTo(DEFAULT_IMPORTE_1);
        assertThat(testFactura.getImporte2()).isEqualTo(DEFAULT_IMPORTE_2);
        assertThat(testFactura.getServicio()).isEqualTo(DEFAULT_SERVICIO);
        assertThat(testFactura.getTarifa()).isEqualTo(DEFAULT_TARIFA);
        assertThat(testFactura.getArchivopdf()).isEqualTo(DEFAULT_ARCHIVOPDF);
        assertThat(testFactura.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFactura.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testFactura.getSocio()).isEqualTo(DEFAULT_SOCIO);
    }

    @Test
    @Transactional
    public void createFacturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();

        // Create the Factura with an existing ID
        factura.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaMockMvc.perform(post("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacturas() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturaList
        restFacturaMockMvc.perform(get("/api/facturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factura.getId().intValue())))
            .andExpect(jsonPath("$.[*].suministro").value(hasItem(DEFAULT_SUMINISTRO)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].inmueble").value(hasItem(DEFAULT_INMUEBLE)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].vencimiento1").value(hasItem(DEFAULT_VENCIMIENTO_1.toString())))
            .andExpect(jsonPath("$.[*].vencimiento2").value(hasItem(DEFAULT_VENCIMIENTO_2.toString())))
            .andExpect(jsonPath("$.[*].importe1").value(hasItem(DEFAULT_IMPORTE_1.intValue())))
            .andExpect(jsonPath("$.[*].importe2").value(hasItem(DEFAULT_IMPORTE_2.intValue())))
            .andExpect(jsonPath("$.[*].servicio").value(hasItem(DEFAULT_SERVICIO)))
            .andExpect(jsonPath("$.[*].tarifa").value(hasItem(DEFAULT_TARIFA)))
            .andExpect(jsonPath("$.[*].archivopdf").value(hasItem(DEFAULT_ARCHIVOPDF)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].socio").value(hasItem(DEFAULT_SOCIO)));
    }
    
    @Test
    @Transactional
    public void getFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", factura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factura.getId().intValue()))
            .andExpect(jsonPath("$.suministro").value(DEFAULT_SUMINISTRO))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.inmueble").value(DEFAULT_INMUEBLE))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.vencimiento1").value(DEFAULT_VENCIMIENTO_1.toString()))
            .andExpect(jsonPath("$.vencimiento2").value(DEFAULT_VENCIMIENTO_2.toString()))
            .andExpect(jsonPath("$.importe1").value(DEFAULT_IMPORTE_1.intValue()))
            .andExpect(jsonPath("$.importe2").value(DEFAULT_IMPORTE_2.intValue()))
            .andExpect(jsonPath("$.servicio").value(DEFAULT_SERVICIO))
            .andExpect(jsonPath("$.tarifa").value(DEFAULT_TARIFA))
            .andExpect(jsonPath("$.archivopdf").value(DEFAULT_ARCHIVOPDF))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.socio").value(DEFAULT_SOCIO));
    }

    @Test
    @Transactional
    public void getNonExistingFactura() throws Exception {
        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura
        Factura updatedFactura = facturaRepository.findById(factura.getId()).get();
        // Disconnect from session so that the updates on updatedFactura are not directly saved in db
        em.detach(updatedFactura);
        updatedFactura
            .suministro(UPDATED_SUMINISTRO)
            .usuario(UPDATED_USUARIO)
            .inmueble(UPDATED_INMUEBLE)
            .periodo(UPDATED_PERIODO)
            .numero(UPDATED_NUMERO)
            .vencimiento1(UPDATED_VENCIMIENTO_1)
            .vencimiento2(UPDATED_VENCIMIENTO_2)
            .importe1(UPDATED_IMPORTE_1)
            .importe2(UPDATED_IMPORTE_2)
            .servicio(UPDATED_SERVICIO)
            .tarifa(UPDATED_TARIFA)
            .archivopdf(UPDATED_ARCHIVOPDF)
            .estado(UPDATED_ESTADO)
            .dni(UPDATED_DNI)
            .socio(UPDATED_SOCIO);

        restFacturaMockMvc.perform(put("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFactura)))
            .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturaList.get(facturaList.size() - 1);
        assertThat(testFactura.getSuministro()).isEqualTo(UPDATED_SUMINISTRO);
        assertThat(testFactura.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testFactura.getInmueble()).isEqualTo(UPDATED_INMUEBLE);
        assertThat(testFactura.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testFactura.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFactura.getVencimiento1()).isEqualTo(UPDATED_VENCIMIENTO_1);
        assertThat(testFactura.getVencimiento2()).isEqualTo(UPDATED_VENCIMIENTO_2);
        assertThat(testFactura.getImporte1()).isEqualTo(UPDATED_IMPORTE_1);
        assertThat(testFactura.getImporte2()).isEqualTo(UPDATED_IMPORTE_2);
        assertThat(testFactura.getServicio()).isEqualTo(UPDATED_SERVICIO);
        assertThat(testFactura.getTarifa()).isEqualTo(UPDATED_TARIFA);
        assertThat(testFactura.getArchivopdf()).isEqualTo(UPDATED_ARCHIVOPDF);
        assertThat(testFactura.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFactura.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testFactura.getSocio()).isEqualTo(UPDATED_SOCIO);
    }

    @Test
    @Transactional
    public void updateNonExistingFactura() throws Exception {
        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Create the Factura

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaMockMvc.perform(put("/api/facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factura)))
            .andExpect(status().isBadRequest());

        // Validate the Factura in the database
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        int databaseSizeBeforeDelete = facturaRepository.findAll().size();

        // Delete the factura
        restFacturaMockMvc.perform(delete("/api/facturas/{id}", factura.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Factura> facturaList = facturaRepository.findAll();
        assertThat(facturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
