package com.ofv.ofv.web.rest;

import com.ofv.ofv.OfVApp;
import com.ofv.ofv.domain.ArchivoFacturas;
import com.ofv.ofv.repository.ArchivoFacturasRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArchivoFacturasResource} REST controller.
 */
@SpringBootTest(classes = OfVApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ArchivoFacturasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_ARCHIVO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHIVO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_BLOB_CONTENT_TYPE = "image/png";

    @Autowired
    private ArchivoFacturasRepository archivoFacturasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArchivoFacturasMockMvc;

    private ArchivoFacturas archivoFacturas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArchivoFacturas createEntity(EntityManager em) {
        ArchivoFacturas archivoFacturas = new ArchivoFacturas()
            .nombre(DEFAULT_NOMBRE)
            .fecha(DEFAULT_FECHA)
            .archivoBlob(DEFAULT_ARCHIVO_BLOB)
            .archivoBlobContentType(DEFAULT_ARCHIVO_BLOB_CONTENT_TYPE);
        return archivoFacturas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArchivoFacturas createUpdatedEntity(EntityManager em) {
        ArchivoFacturas archivoFacturas = new ArchivoFacturas()
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .archivoBlob(UPDATED_ARCHIVO_BLOB)
            .archivoBlobContentType(UPDATED_ARCHIVO_BLOB_CONTENT_TYPE);
        return archivoFacturas;
    }

    @BeforeEach
    public void initTest() {
        archivoFacturas = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivoFacturas() throws Exception {
        int databaseSizeBeforeCreate = archivoFacturasRepository.findAll().size();

        // Create the ArchivoFacturas
        restArchivoFacturasMockMvc.perform(post("/api/archivo-facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivoFacturas)))
            .andExpect(status().isCreated());

        // Validate the ArchivoFacturas in the database
        List<ArchivoFacturas> archivoFacturasList = archivoFacturasRepository.findAll();
        assertThat(archivoFacturasList).hasSize(databaseSizeBeforeCreate + 1);
        ArchivoFacturas testArchivoFacturas = archivoFacturasList.get(archivoFacturasList.size() - 1);
        assertThat(testArchivoFacturas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArchivoFacturas.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testArchivoFacturas.getArchivoBlob()).isEqualTo(DEFAULT_ARCHIVO_BLOB);
        assertThat(testArchivoFacturas.getArchivoBlobContentType()).isEqualTo(DEFAULT_ARCHIVO_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createArchivoFacturasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivoFacturasRepository.findAll().size();

        // Create the ArchivoFacturas with an existing ID
        archivoFacturas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivoFacturasMockMvc.perform(post("/api/archivo-facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivoFacturas)))
            .andExpect(status().isBadRequest());

        // Validate the ArchivoFacturas in the database
        List<ArchivoFacturas> archivoFacturasList = archivoFacturasRepository.findAll();
        assertThat(archivoFacturasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArchivoFacturas() throws Exception {
        // Initialize the database
        archivoFacturasRepository.saveAndFlush(archivoFacturas);

        // Get all the archivoFacturasList
        restArchivoFacturasMockMvc.perform(get("/api/archivo-facturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivoFacturas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].archivoBlobContentType").value(hasItem(DEFAULT_ARCHIVO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO_BLOB))));
    }
    
    @Test
    @Transactional
    public void getArchivoFacturas() throws Exception {
        // Initialize the database
        archivoFacturasRepository.saveAndFlush(archivoFacturas);

        // Get the archivoFacturas
        restArchivoFacturasMockMvc.perform(get("/api/archivo-facturas/{id}", archivoFacturas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archivoFacturas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.archivoBlobContentType").value(DEFAULT_ARCHIVO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivoBlob").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO_BLOB)));
    }

    @Test
    @Transactional
    public void getNonExistingArchivoFacturas() throws Exception {
        // Get the archivoFacturas
        restArchivoFacturasMockMvc.perform(get("/api/archivo-facturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivoFacturas() throws Exception {
        // Initialize the database
        archivoFacturasRepository.saveAndFlush(archivoFacturas);

        int databaseSizeBeforeUpdate = archivoFacturasRepository.findAll().size();

        // Update the archivoFacturas
        ArchivoFacturas updatedArchivoFacturas = archivoFacturasRepository.findById(archivoFacturas.getId()).get();
        // Disconnect from session so that the updates on updatedArchivoFacturas are not directly saved in db
        em.detach(updatedArchivoFacturas);
        updatedArchivoFacturas
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .archivoBlob(UPDATED_ARCHIVO_BLOB)
            .archivoBlobContentType(UPDATED_ARCHIVO_BLOB_CONTENT_TYPE);

        restArchivoFacturasMockMvc.perform(put("/api/archivo-facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArchivoFacturas)))
            .andExpect(status().isOk());

        // Validate the ArchivoFacturas in the database
        List<ArchivoFacturas> archivoFacturasList = archivoFacturasRepository.findAll();
        assertThat(archivoFacturasList).hasSize(databaseSizeBeforeUpdate);
        ArchivoFacturas testArchivoFacturas = archivoFacturasList.get(archivoFacturasList.size() - 1);
        assertThat(testArchivoFacturas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArchivoFacturas.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testArchivoFacturas.getArchivoBlob()).isEqualTo(UPDATED_ARCHIVO_BLOB);
        assertThat(testArchivoFacturas.getArchivoBlobContentType()).isEqualTo(UPDATED_ARCHIVO_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivoFacturas() throws Exception {
        int databaseSizeBeforeUpdate = archivoFacturasRepository.findAll().size();

        // Create the ArchivoFacturas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchivoFacturasMockMvc.perform(put("/api/archivo-facturas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(archivoFacturas)))
            .andExpect(status().isBadRequest());

        // Validate the ArchivoFacturas in the database
        List<ArchivoFacturas> archivoFacturasList = archivoFacturasRepository.findAll();
        assertThat(archivoFacturasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArchivoFacturas() throws Exception {
        // Initialize the database
        archivoFacturasRepository.saveAndFlush(archivoFacturas);

        int databaseSizeBeforeDelete = archivoFacturasRepository.findAll().size();

        // Delete the archivoFacturas
        restArchivoFacturasMockMvc.perform(delete("/api/archivo-facturas/{id}", archivoFacturas.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArchivoFacturas> archivoFacturasList = archivoFacturasRepository.findAll();
        assertThat(archivoFacturasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
