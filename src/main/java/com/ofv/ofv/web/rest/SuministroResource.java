package com.ofv.ofv.web.rest;

import com.ofv.ofv.domain.Suministro;
import com.ofv.ofv.domain.User;
import com.ofv.ofv.repository.SuministroRepository;
import com.ofv.ofv.repository.UserRepository;
import com.ofv.ofv.security.SecurityUtils;
import com.ofv.ofv.service.UserService;
import com.ofv.ofv.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ofv.ofv.domain.Suministro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SuministroResource {

    private final Logger log = LoggerFactory.getLogger(SuministroResource.class);

    private static final String ENTITY_NAME = "suministro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuministroRepository suministroRepository;
    private final UserService uS;

    public SuministroResource(SuministroRepository suministroRepository, UserService uS) {
        this.suministroRepository = suministroRepository;
        this.uS = uS;
    }

    /**
     * {@code POST  /suministros} : Create a new suministro.
     *
     * @param suministro the suministro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suministro, or with status {@code 400 (Bad Request)} if the suministro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suministros")
    public ResponseEntity<Suministro> createSuministro(@RequestBody Suministro suministro) throws URISyntaxException {
        log.debug("REST request to save Suministro : {}", suministro);
        if (suministro.getId() != null) {
            throw new BadRequestAlertException("A new suministro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Suministro result = suministroRepository.save(suministro);
        return ResponseEntity.created(new URI("/api/suministros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/suministros/n/{numero}/{dni}")
    public ResponseEntity<Suministro> createSuministroConNumeroYDNI(@PathVariable String numero, @PathVariable String dni) throws URISyntaxException {

        log.debug("+++++++++++++++++++++++++++++" + suministroRepository.coincideNumeroConDNI(numero, dni));
        if(suministroRepository.coincideNumeroConDNI(numero, dni) == 0) {
            throw new BadRequestAlertException("No coincide número de suministro con DNI", ENTITY_NAME, "dninocoincide");
        }
        
        // select 0 as id, suministro, servicio, inmueble, usuario, dni, tarifa from v_suministros v where trim(v.suministro) = :sumi
        List<Object[]> s = suministroRepository.findEnVistaPorSuministro(numero);

        Suministro toSave = new Suministro();
        toSave.setSuministro(s.get(0)[1].toString());
        toSave.setServicio(s.get(0)[2].toString());
        toSave.setInmueble(s.get(0)[3].toString());
        toSave.setUsuario(s.get(0)[4].toString());
        toSave.setDni(s.get(0)[5].toString());
        toSave.setTarifa(s.get(0)[6].toString());
        
        Optional<User> u = uS.getUserWithAuthorities();
        toSave.addUser(u.get());

        Suministro result = suministroRepository.save(toSave);

        return ResponseEntity.created(new URI("/api/suministros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /suministros} : Updates an existing suministro.
     *
     * @param suministro the suministro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suministro,
     * or with status {@code 400 (Bad Request)} if the suministro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suministro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suministros")
    public ResponseEntity<Suministro> updateSuministro(@RequestBody Suministro suministro) throws URISyntaxException {
        log.debug("REST request to update Suministro : {}", suministro);
        if (suministro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Suministro result = suministroRepository.save(suministro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suministro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /suministros} : get all the suministros.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suministros in body.
     */
    @GetMapping("/suministros")
    public ResponseEntity<List<Suministro>> getAllSuministros(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Suministros");
        Page<Suministro> page;
        if (eagerload) {
            page = suministroRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = suministroRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/suministros/u")
    public List<Suministro> getAllSuministrosByUserid() {
        
        Optional<User> isUser = uS.getUserWithAuthorities();
        User user = isUser.get();

        return suministroRepository.findAllByUserid(user.getId());
    }

   
    public List<Object[]> getAllSuministrosBySocio(String socio) {

        return suministroRepository.findEnVistaPorSocio(socio);
    }

    /**
     * {@code GET  /suministros/:id} : get the "id" suministro.
     *
     * @param id the id of the suministro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suministro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suministros/{id}")
    public ResponseEntity<Suministro> getSuministro(@PathVariable Long id) {
        log.debug("REST request to get Suministro : {}", id);
        Optional<Suministro> suministro = suministroRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(suministro);
    }

    /**
     * {@code DELETE  /suministros/:id} : delete the "id" suministro.
     *
     * @param id the id of the suministro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suministros/{id}")
    public ResponseEntity<Void> deleteSuministro(@PathVariable Long id) {
        log.debug("REST request to delete Suministro : {}", id);
        suministroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
