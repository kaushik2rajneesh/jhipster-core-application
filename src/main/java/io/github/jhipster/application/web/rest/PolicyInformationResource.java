package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.PolicyInformation;
import io.github.jhipster.application.repository.PolicyInformationRepository;
import io.github.jhipster.application.repository.search.PolicyInformationSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.PolicyInformation}.
 */
@RestController
@RequestMapping("/api")
public class PolicyInformationResource {

    private final Logger log = LoggerFactory.getLogger(PolicyInformationResource.class);

    private static final String ENTITY_NAME = "jhipsterCoreApplicationPolicyInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyInformationRepository policyInformationRepository;

    private final PolicyInformationSearchRepository policyInformationSearchRepository;

    public PolicyInformationResource(PolicyInformationRepository policyInformationRepository, PolicyInformationSearchRepository policyInformationSearchRepository) {
        this.policyInformationRepository = policyInformationRepository;
        this.policyInformationSearchRepository = policyInformationSearchRepository;
    }

    /**
     * {@code POST  /policy-informations} : Create a new policyInformation.
     *
     * @param policyInformation the policyInformation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyInformation, or with status {@code 400 (Bad Request)} if the policyInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-informations")
    public ResponseEntity<PolicyInformation> createPolicyInformation(@Valid @RequestBody PolicyInformation policyInformation) throws URISyntaxException {
        log.debug("REST request to save PolicyInformation : {}", policyInformation);
        if (policyInformation.getId() != null) {
            throw new BadRequestAlertException("A new policyInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyInformation result = policyInformationRepository.save(policyInformation);
        policyInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/policy-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-informations} : Updates an existing policyInformation.
     *
     * @param policyInformation the policyInformation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyInformation,
     * or with status {@code 400 (Bad Request)} if the policyInformation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyInformation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-informations")
    public ResponseEntity<PolicyInformation> updatePolicyInformation(@Valid @RequestBody PolicyInformation policyInformation) throws URISyntaxException {
        log.debug("REST request to update PolicyInformation : {}", policyInformation);
        if (policyInformation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PolicyInformation result = policyInformationRepository.save(policyInformation);
        policyInformationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyInformation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policy-informations} : get all the policyInformations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyInformations in body.
     */
    @GetMapping("/policy-informations")
    public List<PolicyInformation> getAllPolicyInformations(@RequestParam(required = false) String filter) {
        if ("insuranceform-is-null".equals(filter)) {
            log.debug("REST request to get all PolicyInformations where insuranceForm is null");
            return StreamSupport
                .stream(policyInformationRepository.findAll().spliterator(), false)
                .filter(policyInformation -> policyInformation.getInsuranceForm() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PolicyInformations");
        return policyInformationRepository.findAll();
    }

    /**
     * {@code GET  /policy-informations/:id} : get the "id" policyInformation.
     *
     * @param id the id of the policyInformation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyInformation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-informations/{id}")
    public ResponseEntity<PolicyInformation> getPolicyInformation(@PathVariable Long id) {
        log.debug("REST request to get PolicyInformation : {}", id);
        Optional<PolicyInformation> policyInformation = policyInformationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(policyInformation);
    }

    /**
     * {@code DELETE  /policy-informations/:id} : delete the "id" policyInformation.
     *
     * @param id the id of the policyInformation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-informations/{id}")
    public ResponseEntity<Void> deletePolicyInformation(@PathVariable Long id) {
        log.debug("REST request to delete PolicyInformation : {}", id);
        policyInformationRepository.deleteById(id);
        policyInformationSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/policy-informations?query=:query} : search for the policyInformation corresponding
     * to the query.
     *
     * @param query the query of the policyInformation search.
     * @return the result of the search.
     */
    @GetMapping("/_search/policy-informations")
    public List<PolicyInformation> searchPolicyInformations(@RequestParam String query) {
        log.debug("REST request to search PolicyInformations for query {}", query);
        return StreamSupport
            .stream(policyInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
