package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterCoreApplicationApp;
import io.github.jhipster.application.domain.PolicyInformation;
import io.github.jhipster.application.domain.InsuranceForm;
import io.github.jhipster.application.repository.PolicyInformationRepository;
import io.github.jhipster.application.repository.search.PolicyInformationSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PolicyInformationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterCoreApplicationApp.class)
public class PolicyInformationResourceIT {

    private static final Boolean DEFAULT_POLICY_NEW_OR_EXISTING = false;
    private static final Boolean UPDATED_POLICY_NEW_OR_EXISTING = true;

    private static final String DEFAULT_POLICY_NO_EXISTING = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NO_EXISTING = "BBBBBBBBBB";

    private static final Integer DEFAULT_INSURANCE_COMPANY_EXISTING = 1;
    private static final Integer UPDATED_INSURANCE_COMPANY_EXISTING = 2;

    private static final Instant DEFAULT_DATE_OF_EXPIRE_EXISTING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_EXPIRE_EXISTING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IDV = "AAAAAAAAAA";
    private static final String UPDATED_IDV = "BBBBBBBBBB";

    private static final String DEFAULT_NCB = "AAAAAAAAAA";
    private static final String UPDATED_NCB = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE_OF_VICHLE = 1L;
    private static final Long UPDATED_VALUE_OF_VICHLE = 2L;

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VICHLE_MANUF_COMP = "AAAAAAAAAA";
    private static final String UPDATED_VICHLE_MANUF_COMP = "BBBBBBBBBB";

    private static final String DEFAULT_POLICY_TPA = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_TPA = "BBBBBBBBBB";

    private static final Integer DEFAULT_POLICY_INFO_GEN_KEY = 1;
    private static final Integer UPDATED_POLICY_INFO_GEN_KEY = 2;

    @Autowired
    private PolicyInformationRepository policyInformationRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.PolicyInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private PolicyInformationSearchRepository mockPolicyInformationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPolicyInformationMockMvc;

    private PolicyInformation policyInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyInformationResource policyInformationResource = new PolicyInformationResource(policyInformationRepository, mockPolicyInformationSearchRepository);
        this.restPolicyInformationMockMvc = MockMvcBuilders.standaloneSetup(policyInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyInformation createEntity(EntityManager em) {
        PolicyInformation policyInformation = new PolicyInformation()
            .policyNewOrExisting(DEFAULT_POLICY_NEW_OR_EXISTING)
            .policyNoExisting(DEFAULT_POLICY_NO_EXISTING)
            .insuranceCompanyExisting(DEFAULT_INSURANCE_COMPANY_EXISTING)
            .dateOfExpireExisting(DEFAULT_DATE_OF_EXPIRE_EXISTING)
            .idv(DEFAULT_IDV)
            .ncb(DEFAULT_NCB)
            .valueOfVichle(DEFAULT_VALUE_OF_VICHLE)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .vichleManufComp(DEFAULT_VICHLE_MANUF_COMP)
            .policyTPA(DEFAULT_POLICY_TPA)
            .policyInfoGenKey(DEFAULT_POLICY_INFO_GEN_KEY);
        // Add required entity
        InsuranceForm insuranceForm;
        if (TestUtil.findAll(em, InsuranceForm.class).isEmpty()) {
            insuranceForm = InsuranceFormResourceIT.createEntity(em);
            em.persist(insuranceForm);
            em.flush();
        } else {
            insuranceForm = TestUtil.findAll(em, InsuranceForm.class).get(0);
        }
        policyInformation.setInsuranceForm(insuranceForm);
        return policyInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyInformation createUpdatedEntity(EntityManager em) {
        PolicyInformation policyInformation = new PolicyInformation()
            .policyNewOrExisting(UPDATED_POLICY_NEW_OR_EXISTING)
            .policyNoExisting(UPDATED_POLICY_NO_EXISTING)
            .insuranceCompanyExisting(UPDATED_INSURANCE_COMPANY_EXISTING)
            .dateOfExpireExisting(UPDATED_DATE_OF_EXPIRE_EXISTING)
            .idv(UPDATED_IDV)
            .ncb(UPDATED_NCB)
            .valueOfVichle(UPDATED_VALUE_OF_VICHLE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .vichleManufComp(UPDATED_VICHLE_MANUF_COMP)
            .policyTPA(UPDATED_POLICY_TPA)
            .policyInfoGenKey(UPDATED_POLICY_INFO_GEN_KEY);
        // Add required entity
        InsuranceForm insuranceForm;
        if (TestUtil.findAll(em, InsuranceForm.class).isEmpty()) {
            insuranceForm = InsuranceFormResourceIT.createUpdatedEntity(em);
            em.persist(insuranceForm);
            em.flush();
        } else {
            insuranceForm = TestUtil.findAll(em, InsuranceForm.class).get(0);
        }
        policyInformation.setInsuranceForm(insuranceForm);
        return policyInformation;
    }

    @BeforeEach
    public void initTest() {
        policyInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyInformation() throws Exception {
        int databaseSizeBeforeCreate = policyInformationRepository.findAll().size();

        // Create the PolicyInformation
        restPolicyInformationMockMvc.perform(post("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyInformation)))
            .andExpect(status().isCreated());

        // Validate the PolicyInformation in the database
        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyInformation testPolicyInformation = policyInformationList.get(policyInformationList.size() - 1);
        assertThat(testPolicyInformation.isPolicyNewOrExisting()).isEqualTo(DEFAULT_POLICY_NEW_OR_EXISTING);
        assertThat(testPolicyInformation.getPolicyNoExisting()).isEqualTo(DEFAULT_POLICY_NO_EXISTING);
        assertThat(testPolicyInformation.getInsuranceCompanyExisting()).isEqualTo(DEFAULT_INSURANCE_COMPANY_EXISTING);
        assertThat(testPolicyInformation.getDateOfExpireExisting()).isEqualTo(DEFAULT_DATE_OF_EXPIRE_EXISTING);
        assertThat(testPolicyInformation.getIdv()).isEqualTo(DEFAULT_IDV);
        assertThat(testPolicyInformation.getNcb()).isEqualTo(DEFAULT_NCB);
        assertThat(testPolicyInformation.getValueOfVichle()).isEqualTo(DEFAULT_VALUE_OF_VICHLE);
        assertThat(testPolicyInformation.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testPolicyInformation.getVichleManufComp()).isEqualTo(DEFAULT_VICHLE_MANUF_COMP);
        assertThat(testPolicyInformation.getPolicyTPA()).isEqualTo(DEFAULT_POLICY_TPA);
        assertThat(testPolicyInformation.getPolicyInfoGenKey()).isEqualTo(DEFAULT_POLICY_INFO_GEN_KEY);

        // Validate the PolicyInformation in Elasticsearch
        verify(mockPolicyInformationSearchRepository, times(1)).save(testPolicyInformation);
    }

    @Test
    @Transactional
    public void createPolicyInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyInformationRepository.findAll().size();

        // Create the PolicyInformation with an existing ID
        policyInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyInformationMockMvc.perform(post("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyInformation)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyInformation in the database
        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the PolicyInformation in Elasticsearch
        verify(mockPolicyInformationSearchRepository, times(0)).save(policyInformation);
    }


    @Test
    @Transactional
    public void checkPolicyNewOrExistingIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyInformationRepository.findAll().size();
        // set the field null
        policyInformation.setPolicyNewOrExisting(null);

        // Create the PolicyInformation, which fails.

        restPolicyInformationMockMvc.perform(post("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyInformation)))
            .andExpect(status().isBadRequest());

        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPolicyInfoGenKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyInformationRepository.findAll().size();
        // set the field null
        policyInformation.setPolicyInfoGenKey(null);

        // Create the PolicyInformation, which fails.

        restPolicyInformationMockMvc.perform(post("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyInformation)))
            .andExpect(status().isBadRequest());

        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolicyInformations() throws Exception {
        // Initialize the database
        policyInformationRepository.saveAndFlush(policyInformation);

        // Get all the policyInformationList
        restPolicyInformationMockMvc.perform(get("/api/policy-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNewOrExisting").value(hasItem(DEFAULT_POLICY_NEW_OR_EXISTING.booleanValue())))
            .andExpect(jsonPath("$.[*].policyNoExisting").value(hasItem(DEFAULT_POLICY_NO_EXISTING.toString())))
            .andExpect(jsonPath("$.[*].insuranceCompanyExisting").value(hasItem(DEFAULT_INSURANCE_COMPANY_EXISTING)))
            .andExpect(jsonPath("$.[*].dateOfExpireExisting").value(hasItem(DEFAULT_DATE_OF_EXPIRE_EXISTING.toString())))
            .andExpect(jsonPath("$.[*].idv").value(hasItem(DEFAULT_IDV.toString())))
            .andExpect(jsonPath("$.[*].ncb").value(hasItem(DEFAULT_NCB.toString())))
            .andExpect(jsonPath("$.[*].valueOfVichle").value(hasItem(DEFAULT_VALUE_OF_VICHLE.intValue())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vichleManufComp").value(hasItem(DEFAULT_VICHLE_MANUF_COMP.toString())))
            .andExpect(jsonPath("$.[*].policyTPA").value(hasItem(DEFAULT_POLICY_TPA.toString())))
            .andExpect(jsonPath("$.[*].policyInfoGenKey").value(hasItem(DEFAULT_POLICY_INFO_GEN_KEY)));
    }
    
    @Test
    @Transactional
    public void getPolicyInformation() throws Exception {
        // Initialize the database
        policyInformationRepository.saveAndFlush(policyInformation);

        // Get the policyInformation
        restPolicyInformationMockMvc.perform(get("/api/policy-informations/{id}", policyInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyInformation.getId().intValue()))
            .andExpect(jsonPath("$.policyNewOrExisting").value(DEFAULT_POLICY_NEW_OR_EXISTING.booleanValue()))
            .andExpect(jsonPath("$.policyNoExisting").value(DEFAULT_POLICY_NO_EXISTING.toString()))
            .andExpect(jsonPath("$.insuranceCompanyExisting").value(DEFAULT_INSURANCE_COMPANY_EXISTING))
            .andExpect(jsonPath("$.dateOfExpireExisting").value(DEFAULT_DATE_OF_EXPIRE_EXISTING.toString()))
            .andExpect(jsonPath("$.idv").value(DEFAULT_IDV.toString()))
            .andExpect(jsonPath("$.ncb").value(DEFAULT_NCB.toString()))
            .andExpect(jsonPath("$.valueOfVichle").value(DEFAULT_VALUE_OF_VICHLE.intValue()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.vichleManufComp").value(DEFAULT_VICHLE_MANUF_COMP.toString()))
            .andExpect(jsonPath("$.policyTPA").value(DEFAULT_POLICY_TPA.toString()))
            .andExpect(jsonPath("$.policyInfoGenKey").value(DEFAULT_POLICY_INFO_GEN_KEY));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyInformation() throws Exception {
        // Get the policyInformation
        restPolicyInformationMockMvc.perform(get("/api/policy-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyInformation() throws Exception {
        // Initialize the database
        policyInformationRepository.saveAndFlush(policyInformation);

        int databaseSizeBeforeUpdate = policyInformationRepository.findAll().size();

        // Update the policyInformation
        PolicyInformation updatedPolicyInformation = policyInformationRepository.findById(policyInformation.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyInformation are not directly saved in db
        em.detach(updatedPolicyInformation);
        updatedPolicyInformation
            .policyNewOrExisting(UPDATED_POLICY_NEW_OR_EXISTING)
            .policyNoExisting(UPDATED_POLICY_NO_EXISTING)
            .insuranceCompanyExisting(UPDATED_INSURANCE_COMPANY_EXISTING)
            .dateOfExpireExisting(UPDATED_DATE_OF_EXPIRE_EXISTING)
            .idv(UPDATED_IDV)
            .ncb(UPDATED_NCB)
            .valueOfVichle(UPDATED_VALUE_OF_VICHLE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .vichleManufComp(UPDATED_VICHLE_MANUF_COMP)
            .policyTPA(UPDATED_POLICY_TPA)
            .policyInfoGenKey(UPDATED_POLICY_INFO_GEN_KEY);

        restPolicyInformationMockMvc.perform(put("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicyInformation)))
            .andExpect(status().isOk());

        // Validate the PolicyInformation in the database
        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeUpdate);
        PolicyInformation testPolicyInformation = policyInformationList.get(policyInformationList.size() - 1);
        assertThat(testPolicyInformation.isPolicyNewOrExisting()).isEqualTo(UPDATED_POLICY_NEW_OR_EXISTING);
        assertThat(testPolicyInformation.getPolicyNoExisting()).isEqualTo(UPDATED_POLICY_NO_EXISTING);
        assertThat(testPolicyInformation.getInsuranceCompanyExisting()).isEqualTo(UPDATED_INSURANCE_COMPANY_EXISTING);
        assertThat(testPolicyInformation.getDateOfExpireExisting()).isEqualTo(UPDATED_DATE_OF_EXPIRE_EXISTING);
        assertThat(testPolicyInformation.getIdv()).isEqualTo(UPDATED_IDV);
        assertThat(testPolicyInformation.getNcb()).isEqualTo(UPDATED_NCB);
        assertThat(testPolicyInformation.getValueOfVichle()).isEqualTo(UPDATED_VALUE_OF_VICHLE);
        assertThat(testPolicyInformation.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testPolicyInformation.getVichleManufComp()).isEqualTo(UPDATED_VICHLE_MANUF_COMP);
        assertThat(testPolicyInformation.getPolicyTPA()).isEqualTo(UPDATED_POLICY_TPA);
        assertThat(testPolicyInformation.getPolicyInfoGenKey()).isEqualTo(UPDATED_POLICY_INFO_GEN_KEY);

        // Validate the PolicyInformation in Elasticsearch
        verify(mockPolicyInformationSearchRepository, times(1)).save(testPolicyInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyInformation() throws Exception {
        int databaseSizeBeforeUpdate = policyInformationRepository.findAll().size();

        // Create the PolicyInformation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyInformationMockMvc.perform(put("/api/policy-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyInformation)))
            .andExpect(status().isBadRequest());

        // Validate the PolicyInformation in the database
        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PolicyInformation in Elasticsearch
        verify(mockPolicyInformationSearchRepository, times(0)).save(policyInformation);
    }

    @Test
    @Transactional
    public void deletePolicyInformation() throws Exception {
        // Initialize the database
        policyInformationRepository.saveAndFlush(policyInformation);

        int databaseSizeBeforeDelete = policyInformationRepository.findAll().size();

        // Delete the policyInformation
        restPolicyInformationMockMvc.perform(delete("/api/policy-informations/{id}", policyInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PolicyInformation> policyInformationList = policyInformationRepository.findAll();
        assertThat(policyInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PolicyInformation in Elasticsearch
        verify(mockPolicyInformationSearchRepository, times(1)).deleteById(policyInformation.getId());
    }

    @Test
    @Transactional
    public void searchPolicyInformation() throws Exception {
        // Initialize the database
        policyInformationRepository.saveAndFlush(policyInformation);
        when(mockPolicyInformationSearchRepository.search(queryStringQuery("id:" + policyInformation.getId())))
            .thenReturn(Collections.singletonList(policyInformation));
        // Search the policyInformation
        restPolicyInformationMockMvc.perform(get("/api/_search/policy-informations?query=id:" + policyInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNewOrExisting").value(hasItem(DEFAULT_POLICY_NEW_OR_EXISTING.booleanValue())))
            .andExpect(jsonPath("$.[*].policyNoExisting").value(hasItem(DEFAULT_POLICY_NO_EXISTING)))
            .andExpect(jsonPath("$.[*].insuranceCompanyExisting").value(hasItem(DEFAULT_INSURANCE_COMPANY_EXISTING)))
            .andExpect(jsonPath("$.[*].dateOfExpireExisting").value(hasItem(DEFAULT_DATE_OF_EXPIRE_EXISTING.toString())))
            .andExpect(jsonPath("$.[*].idv").value(hasItem(DEFAULT_IDV)))
            .andExpect(jsonPath("$.[*].ncb").value(hasItem(DEFAULT_NCB)))
            .andExpect(jsonPath("$.[*].valueOfVichle").value(hasItem(DEFAULT_VALUE_OF_VICHLE.intValue())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vichleManufComp").value(hasItem(DEFAULT_VICHLE_MANUF_COMP)))
            .andExpect(jsonPath("$.[*].policyTPA").value(hasItem(DEFAULT_POLICY_TPA)))
            .andExpect(jsonPath("$.[*].policyInfoGenKey").value(hasItem(DEFAULT_POLICY_INFO_GEN_KEY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyInformation.class);
        PolicyInformation policyInformation1 = new PolicyInformation();
        policyInformation1.setId(1L);
        PolicyInformation policyInformation2 = new PolicyInformation();
        policyInformation2.setId(policyInformation1.getId());
        assertThat(policyInformation1).isEqualTo(policyInformation2);
        policyInformation2.setId(2L);
        assertThat(policyInformation1).isNotEqualTo(policyInformation2);
        policyInformation1.setId(null);
        assertThat(policyInformation1).isNotEqualTo(policyInformation2);
    }
}
