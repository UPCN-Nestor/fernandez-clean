package com.ofv.ofv.web.rest;

import com.ofv.ofv.OfVApp;
import com.ofv.ofv.domain.Suministro;
import com.ofv.ofv.repository.SuministroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SuministroResource} REST controller.
 */
@SpringBootTest(classes = OfVApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SuministroResourceIT {

    private static final String DEFAULT_SUMINISTRO = "AAAAAAAAAA";
    private static final String UPDATED_SUMINISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_INMUEBLE = "AAAAAAAAAA";
    private static final String UPDATED_INMUEBLE = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final String DEFAULT_TARIFA = "AAAAAAAAAA";
    private static final String UPDATED_TARIFA = "BBBBBBBBBB";

    @Autowired
    private SuministroRepository suministroRepository;

    @Mock
    private SuministroRepository suministroRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuministroMockMvc;

    private Suministro suministro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suministro createEntity(EntityManager em) {
        Suministro suministro = new Suministro()
            .suministro(DEFAULT_SUMINISTRO)
            .servicio(DEFAULT_SERVICIO)
            .inmueble(DEFAULT_INMUEBLE)
            .usuario(DEFAULT_USUARIO)
            .dni(DEFAULT_DNI)
            .tarifa(DEFAULT_TARIFA);
        return suministro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suministro createUpdatedEntity(EntityManager em) {
        Suministro suministro = new Suministro()
            .suministro(UPDATED_SUMINISTRO)
            .servicio(UPDATED_SERVICIO)
            .inmueble(UPDATED_INMUEBLE)
            .usuario(UPDATED_USUARIO)
            .dni(UPDATED_DNI)
            .tarifa(UPDATED_TARIFA);
        return suministro;
    }

    @BeforeEach
    public void initTest() {
        suministro = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuministro() throws Exception {
        int databaseSizeBeforeCreate = suministroRepository.findAll().size();

        // Create the Suministro
        restSuministroMockMvc.perform(post("/api/suministros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suministro)))
            .andExpect(status().isCreated());

        // Validate the Suministro in the database
        List<Suministro> suministroList = suministroRepository.findAll();
        assertThat(suministroList).hasSize(databaseSizeBeforeCreate + 1);
        Suministro testSuministro = suministroList.get(suministroList.size() - 1);
        assertThat(testSuministro.getSuministro()).isEqualTo(DEFAULT_SUMINISTRO);
        assertThat(testSuministro.getServicio()).isEqualTo(DEFAULT_SERVICIO);
        assertThat(testSuministro.getInmueble()).isEqualTo(DEFAULT_INMUEBLE);
        assertThat(testSuministro.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testSuministro.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testSuministro.getTarifa()).isEqualTo(DEFAULT_TARIFA);
    }

    @Test
    @Transactional
    public void createSuministroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suministroRepository.findAll().size();

        // Create the Suministro with an existing ID
        suministro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuministroMockMvc.perform(post("/api/suministros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suministro)))
            .andExpect(status().isBadRequest());

        // Validate the Suministro in the database
        List<Suministro> suministroList = suministroRepository.findAll();
        assertThat(suministroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSuministros() throws Exception {
        // Initialize the database
        suministroRepository.saveAndFlush(suministro);

        // Get all the suministroList
        restSuministroMockMvc.perform(get("/api/suministros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suministro.getId().intValue())))
            .andExpect(jsonPath("$.[*].suministro").value(hasItem(DEFAULT_SUMINISTRO)))
            .andExpect(jsonPath("$.[*].servicio").value(hasItem(DEFAULT_SERVICIO)))
            .andExpect(jsonPath("$.[*].inmueble").value(hasItem(DEFAULT_INMUEBLE)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].tarifa").value(hasItem(DEFAULT_TARIFA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSuministrosWithEagerRelationshipsIsEnabled() throws Exception {
        SuministroResource suministroResource = new SuministroResource(suministroRepositoryMock);
        when(suministroRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSuministroMockMvc.perform(get("/api/suministros?eagerload=true"))
            .andExpect(status().isOk());

        verify(suministroRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSuministrosWithEagerRelationshipsIsNotEnabled() throws Exception {
        SuministroResource suministroResource = new SuministroResource(suministroRepositoryMock);
        when(suministroRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSuministroMockMvc.perform(get("/api/suministros?eagerload=true"))
            .andExpect(status().isOk());

        verify(suministroRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSuministro() throws Exception {
        // Initialize the database
        suministroRepository.saveAndFlush(suministro);

        // Get the suministro
        restSuministroMockMvc.perform(get("/api/suministros/{id}", suministro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suministro.getId().intValue()))
            .andExpect(jsonPath("$.suministro").value(DEFAULT_SUMINISTRO))
            .andExpect(jsonPath("$.servicio").value(DEFAULT_SERVICIO))
            .andExpect(jsonPath("$.inmueble").value(DEFAULT_INMUEBLE))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.tarifa").value(DEFAULT_TARIFA));
    }

    @Test
    @Transactional
    public void getNonExistingSuministro() throws Exception {
        // Get the suministro
        restSuministroMockMvc.perform(get("/api/suministros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuministro() throws Exception {
        // Initialize the database
        suministroRepository.saveAndFlush(suministro);

        int databaseSizeBeforeUpdate = suministroRepository.findAll().size();

        // Update the suministro
        Suministro updatedSuministro = suministroRepository.findById(suministro.getId()).get();
        // Disconnect from session so that the updates on updatedSuministro are not directly saved in db
        em.detach(updatedSuministro);
        updatedSuministro
            .suministro(UPDATED_SUMINISTRO)
            .servicio(UPDATED_SERVICIO)
            .inmueble(UPDATED_INMUEBLE)
            .usuario(UPDATED_USUARIO)
            .dni(UPDATED_DNI)
            .tarifa(UPDATED_TARIFA);

        restSuministroMockMvc.perform(put("/api/suministros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuministro)))
            .andExpect(status().isOk());

        // Validate the Suministro in the database
        List<Suministro> suministroList = suministroRepository.findAll();
        assertThat(suministroList).hasSize(databaseSizeBeforeUpdate);
        Suministro testSuministro = suministroList.get(suministroList.size() - 1);
        assertThat(testSuministro.getSuministro()).isEqualTo(UPDATED_SUMINISTRO);
        assertThat(testSuministro.getServicio()).isEqualTo(UPDATED_SERVICIO);
        assertThat(testSuministro.getInmueble()).isEqualTo(UPDATED_INMUEBLE);
        assertThat(testSuministro.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testSuministro.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testSuministro.getTarifa()).isEqualTo(UPDATED_TARIFA);
    }

    @Test
    @Transactional
    public void updateNonExistingSuministro() throws Exception {
        int databaseSizeBeforeUpdate = suministroRepository.findAll().size();

        // Create the Suministro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuministroMockMvc.perform(put("/api/suministros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suministro)))
            .andExpect(status().isBadRequest());

        // Validate the Suministro in the database
        List<Suministro> suministroList = suministroRepository.findAll();
        assertThat(suministroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuministro() throws Exception {
        // Initialize the database
        suministroRepository.saveAndFlush(suministro);

        int databaseSizeBeforeDelete = suministroRepository.findAll().size();

        // Delete the suministro
        restSuministroMockMvc.perform(delete("/api/suministros/{id}", suministro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suministro> suministroList = suministroRepository.findAll();
        assertThat(suministroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
