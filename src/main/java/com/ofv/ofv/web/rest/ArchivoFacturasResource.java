package com.ofv.ofv.web.rest;

import com.ofv.ofv.domain.ArchivoFacturas;
import com.ofv.ofv.domain.Factura;
import com.ofv.ofv.repository.ArchivoFacturasRepository;
import com.ofv.ofv.repository.FacturaRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.sql.rowset.serial.SerialException;

/**
 * REST controller for managing {@link com.ofv.ofv.domain.ArchivoFacturas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArchivoFacturasResource {

    private final Logger log = LoggerFactory.getLogger(ArchivoFacturasResource.class);

    private static final String ENTITY_NAME = "archivoFacturas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchivoFacturasRepository archivoFacturasRepository;
    private final FacturaRepository fR;

    public ArchivoFacturasResource(ArchivoFacturasRepository archivoFacturasRepository, FacturaRepository fR) {
        this.archivoFacturasRepository = archivoFacturasRepository;
        this.fR = fR;
    }

    /**
     * {@code POST  /archivo-facturas} : Create a new archivoFacturas.
     *
     * @param archivoFacturas the archivoFacturas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new archivoFacturas, or with status
     *         {@code 400 (Bad Request)} if the archivoFacturas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/archivo-facturas")
    public ResponseEntity<ArchivoFacturas> createArchivoFacturas(@RequestBody ArchivoFacturas archivoFacturas)
            throws URISyntaxException {
        log.debug("REST request to save ArchivoFacturas : {}", archivoFacturas);
        if (archivoFacturas.getId() != null) {
            throw new BadRequestAlertException("A new archivoFacturas cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }

        byte[] csvData = archivoFacturas.getArchivoCsv().clone();
        archivoFacturas.setArchivoCsv(null);
        archivoFacturas.setArchivoBlob(null);
        ArchivoFacturas result = archivoFacturasRepository.save(archivoFacturas);

        String csvPath = "e:/FernandezOfV/facturas" + LocalDate.now() + ".csv";

        Blob csv;
        try {
            csv = new javax.sql.rowset.serial.SerialBlob(csvData);

            // InputStream in = blob.getBinaryStream();
            File file = new File(csvPath);
            FileOutputStream out = new FileOutputStream(file);
            byte[] buff = csv.getBytes(1, (int) csv.length());
            out.write(buff);
            out.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        procesarCsv(csvPath, result);

        return ResponseEntity
                .created(new URI("/api/archivo-facturas/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /archivo-facturas} : Updates an existing archivoFacturas.
     *
     * @param archivoFacturas the archivoFacturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated archivoFacturas, or with status {@code 400 (Bad Request)}
     *         if the archivoFacturas is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the archivoFacturas couldn't
     *         be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/archivo-facturas")
    public ResponseEntity<ArchivoFacturas> updateArchivoFacturas(@RequestBody ArchivoFacturas archivoFacturas)
            throws URISyntaxException {
        log.debug("REST request to update ArchivoFacturas : {}", archivoFacturas);
        if (archivoFacturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        ArchivoFacturas result = archivoFacturasRepository.save(archivoFacturas);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                archivoFacturas.getId().toString())).body(result);
    }

    /**
     * {@code GET  /archivo-facturas} : get all the archivoFacturas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of archivoFacturas in body.
     */
    @GetMapping("/archivo-facturas")
    public ResponseEntity<List<ArchivoFacturas>> getAllArchivoFacturas(Pageable pageable) {
        log.debug("REST request to get a page of ArchivoFacturas");
        Page<ArchivoFacturas> page = archivoFacturasRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /archivo-facturas/:id} : get the "id" archivoFacturas.
     *
     * @param id the id of the archivoFacturas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the archivoFacturas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/archivo-facturas/{id}")
    public ResponseEntity<ArchivoFacturas> getArchivoFacturas(@PathVariable Long id) {
        log.debug("REST request to get ArchivoFacturas : {}", id);
        Optional<ArchivoFacturas> archivoFacturas = archivoFacturasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(archivoFacturas);
    }

    /**
     * {@code DELETE  /archivo-facturas/:id} : delete the "id" archivoFacturas.
     *
     * @param id the id of the archivoFacturas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/archivo-facturas/{id}")
    public ResponseEntity<Void> deleteArchivoFacturas(@PathVariable Long id) {
        log.debug("REST request to delete ArchivoFacturas : {}", id);
        archivoFacturasRepository.deleteById(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    @PostMapping(consumes = { "multipart/mixed", "multipart/form-data" }, value = "/archivo-facturas/multipart")
    ResponseEntity<Object> multipartUpload(@RequestPart(value = "file") MultipartFile file) {
        log.info("Received request with file {}", file.getOriginalFilename());
        File f = new File("e:/FernandezOfV/facturas" + LocalDate.now() + ".zip");
        try {
            file.transferTo(f);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        procesarZips(f);

        return ResponseEntity.accepted().build();
    }

    
    public void procesarZips(File file) {
        
        String zipPath = "e:/FernandezOfV/facturas" +  LocalDate.now() + ".zip";
        String unzipPath = "e:/FernandezOfV/facturas" +  LocalDate.now()  + "/";

        Blob blob;
        try {

            unZipIt(zipPath, unzipPath);


        } catch (Exception e) {
            
        } 
    }


    
    public void procesarCsv(String csvFile, ArchivoFacturas archivo) {

        String line = "";
        String cvsSplitBy = "\\|";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Header
            while((line = br.readLine()) != null) {
                String[] lineSplit = line.split(cvsSplitBy);

                Factura nueva = null;
                try{
                
                    nueva = new Factura();
                if(lineSplit.length > 1) nueva.setSuministro(lineSplit[1]);
                if(lineSplit.length > 2) nueva.setUsuario(lineSplit[2]);
                if(lineSplit.length > 3) nueva.setInmueble(lineSplit[3]);
                if(lineSplit.length > 4) nueva.setPeriodo(lineSplit[4]);
                if(lineSplit.length > 5) nueva.setNumero(lineSplit[5]);
                if(lineSplit.length > 6) nueva.setVencimiento1(LocalDate.parse(lineSplit[6]));
                if(lineSplit.length > 7) nueva.setVencimiento2(LocalDate.parse(lineSplit[7]));
                if(lineSplit.length > 8) nueva.setImporte1(new BigDecimal(lineSplit[8]));
                if(lineSplit.length > 9) nueva.setImporte2(new BigDecimal(lineSplit[9]));
                if(lineSplit.length > 10) nueva.setServicio(lineSplit[10]);
                if(lineSplit.length > 11) nueva.setTarifa(lineSplit[11]);
                if(lineSplit.length > 12) nueva.setArchivopdf(lineSplit[12]);
                if(lineSplit.length > 13) nueva.setEstado(lineSplit[13]);
                if(lineSplit.length > 15) nueva.setDni(lineSplit[15]);
                if(lineSplit.length > 16) nueva.setSocio(lineSplit[16]);
                nueva.setArchivoFacturas(archivo);
                fR.save(nueva);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                /*
                for(String campo : lineSplit) {
                    System.out.println(campo);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









    // AUXs

    public void unZipIt(String zipFile, String outputFolder){

        byte[] buffer = new byte[1024];
   
        try{
   
           //create output directory is not exists
           File folder = new File(outputFolder);
           if(!folder.exists()){
               folder.mkdir();
           }
   
           //get the zip file content
           ZipInputStream zis =
               new ZipInputStream(new FileInputStream(zipFile));
           //get the zipped file list entry
           ZipEntry ze = zis.getNextEntry();
   
           while(ze!=null){
   
              String fileName = ze.getName();
              File newFile = new File(outputFolder + File.separator + fileName);
   
              //System.out.println("file unzip : "+ newFile.getAbsoluteFile());
   
               //create all non exists folders
               //else you will hit FileNotFoundException for compressed folder
               new File(newFile.getParent()).mkdirs();
   
               FileOutputStream fos = new FileOutputStream(newFile);
   
               int len;
               while ((len = zis.read(buffer)) > 0) {
                  fos.write(buffer, 0, len);
               }
   
               fos.close();
               ze = zis.getNextEntry();
           }
   
           zis.closeEntry();
           zis.close();
   
           System.out.println("Done");
   
       }catch(IOException ex){
          ex.printStackTrace();
       }
      }
    
}
