package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HouseFarm;
import com.mycompany.myapp.repository.HouseFarmRepository;
import com.mycompany.myapp.service.HouseFarmQueryService;
import com.mycompany.myapp.service.dto.HouseFarmCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.HouseFarm}.
 */
@RestController
@RequestMapping("/api")
public class HouseFarmResource {

    private final Logger log = LoggerFactory.getLogger(HouseFarmResource.class);

    private static final String ENTITY_NAME = "houseFarm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HouseFarmRepository houseFarmRepository;

    private final HouseFarmQueryService houseFarmQueryService;

    public HouseFarmResource(HouseFarmRepository houseFarmRepository, HouseFarmQueryService houseFarmQueryService) {
        this.houseFarmRepository = houseFarmRepository;
        this.houseFarmQueryService = houseFarmQueryService;
    }

    /**
     * {@code POST  /house-farms} : Create a new houseFarm.
     *
     * @param houseFarm the houseFarm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new houseFarm, or with status {@code 400 (Bad Request)} if the houseFarm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/house-farms")
    public ResponseEntity<HouseFarm> createHouseFarm(@RequestBody HouseFarm houseFarm) throws URISyntaxException {
        log.debug("REST request to save HouseFarm : {}", houseFarm);
        if (houseFarm.getId() != null) {
            throw new BadRequestAlertException("A new houseFarm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HouseFarm result = houseFarmRepository.save(houseFarm);
        return ResponseEntity.created(new URI("/api/house-farms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /house-farms} : Updates an existing houseFarm.
     *
     * @param houseFarm the houseFarm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated houseFarm,
     * or with status {@code 400 (Bad Request)} if the houseFarm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the houseFarm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/house-farms")
    public ResponseEntity<HouseFarm> updateHouseFarm(@RequestBody HouseFarm houseFarm) throws URISyntaxException {
        log.debug("REST request to update HouseFarm : {}", houseFarm);
        if (houseFarm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HouseFarm result = houseFarmRepository.save(houseFarm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, houseFarm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /house-farms} : get all the houseFarms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of houseFarms in body.
     */
    @GetMapping("/house-farms")
    public ResponseEntity<List<HouseFarm>> getAllHouseFarms(HouseFarmCriteria criteria) {
        log.debug("REST request to get HouseFarms by criteria: {}", criteria);
        List<HouseFarm> entityList = houseFarmQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /house-farms/count} : count all the houseFarms.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/house-farms/count")
    public ResponseEntity<Long> countHouseFarms(HouseFarmCriteria criteria) {
        log.debug("REST request to count HouseFarms by criteria: {}", criteria);
        return ResponseEntity.ok().body(houseFarmQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /house-farms/:id} : get the "id" houseFarm.
     *
     * @param id the id of the houseFarm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the houseFarm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/house-farms/{id}")
    public ResponseEntity<HouseFarm> getHouseFarm(@PathVariable Long id) {
        log.debug("REST request to get HouseFarm : {}", id);
        Optional<HouseFarm> houseFarm = houseFarmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(houseFarm);
    }

    /**
     * {@code DELETE  /house-farms/:id} : delete the "id" houseFarm.
     *
     * @param id the id of the houseFarm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/house-farms/{id}")
    public ResponseEntity<Void> deleteHouseFarm(@PathVariable Long id) {
        log.debug("REST request to delete HouseFarm : {}", id);
        houseFarmRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName,
            true, ENTITY_NAME, id.toString())).build();
    }
}
