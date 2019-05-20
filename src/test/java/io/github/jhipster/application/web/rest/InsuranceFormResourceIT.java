package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterCoreApplicationApp;
import io.github.jhipster.application.domain.InsuranceForm;
import io.github.jhipster.application.repository.InsuranceFormRepository;
import io.github.jhipster.application.repository.search.InsuranceFormSearchRepository;
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
 * Integration tests for the {@Link InsuranceFormResource} REST controller.
 */
@SpringBootTest(classes = JhipsterCoreApplicationApp.class)
public class InsuranceFormResourceIT {

    private static final Integer DEFAULT_POLICIES_CATEGORY = 1;
    private static final Integer UPDATED_POLICIES_CATEGORY = 2;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;

    private static final Integer DEFAULT_PHONE_NUMBER = 1;
    private static final Integer UPDATED_PHONE_NUMBER = 2;

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_APPLICATION_GEN_KEY = 1;
    private static final Integer UPDATED_APPLICATION_GEN_KEY = 2;

    @Autowired
    private InsuranceFormRepository insuranceFormRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.InsuranceFormSearchRepositoryMockConfiguration
     */
    @Autowired
    private InsuranceFormSearchRepository mockInsuranceFormSearchRepository;

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

    private MockMvc restInsuranceFormMockMvc;

    private InsuranceForm insuranceForm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceFormResource insuranceFormResource = new InsuranceFormResource(insuranceFormRepository, mockInsuranceFormSearchRepository);
        this.restInsuranceFormMockMvc = MockMvcBuilders.standaloneSetup(insuranceFormResource)
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
    public static InsuranceForm createEntity(EntityManager em) {
        InsuranceForm insuranceForm = new InsuranceForm()
            .policiesCategory(DEFAULT_POLICIES_CATEGORY)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .pincode(DEFAULT_PINCODE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailId(DEFAULT_EMAIL_ID)
            .applicationGenKey(DEFAULT_APPLICATION_GEN_KEY);
        return insuranceForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceForm createUpdatedEntity(EntityManager em) {
        InsuranceForm insuranceForm = new InsuranceForm()
            .policiesCategory(UPDATED_POLICIES_CATEGORY)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .pincode(UPDATED_PINCODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .applicationGenKey(UPDATED_APPLICATION_GEN_KEY);
        return insuranceForm;
    }

    @BeforeEach
    public void initTest() {
        insuranceForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceForm() throws Exception {
        int databaseSizeBeforeCreate = insuranceFormRepository.findAll().size();

        // Create the InsuranceForm
        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isCreated());

        // Validate the InsuranceForm in the database
        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceForm testInsuranceForm = insuranceFormList.get(insuranceFormList.size() - 1);
        assertThat(testInsuranceForm.getPoliciesCategory()).isEqualTo(DEFAULT_POLICIES_CATEGORY);
        assertThat(testInsuranceForm.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testInsuranceForm.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testInsuranceForm.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testInsuranceForm.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInsuranceForm.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testInsuranceForm.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testInsuranceForm.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testInsuranceForm.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testInsuranceForm.getApplicationGenKey()).isEqualTo(DEFAULT_APPLICATION_GEN_KEY);

        // Validate the InsuranceForm in Elasticsearch
        verify(mockInsuranceFormSearchRepository, times(1)).save(testInsuranceForm);
    }

    @Test
    @Transactional
    public void createInsuranceFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceFormRepository.findAll().size();

        // Create the InsuranceForm with an existing ID
        insuranceForm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceForm in the database
        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeCreate);

        // Validate the InsuranceForm in Elasticsearch
        verify(mockInsuranceFormSearchRepository, times(0)).save(insuranceForm);
    }


    @Test
    @Transactional
    public void checkPoliciesCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setPoliciesCategory(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setFirstName(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiddleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setMiddleName(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setLastName(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setAddress(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setCity(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setPincode(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setPhoneNumber(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setEmailId(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationGenKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceFormRepository.findAll().size();
        // set the field null
        insuranceForm.setApplicationGenKey(null);

        // Create the InsuranceForm, which fails.

        restInsuranceFormMockMvc.perform(post("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInsuranceForms() throws Exception {
        // Initialize the database
        insuranceFormRepository.saveAndFlush(insuranceForm);

        // Get all the insuranceFormList
        restInsuranceFormMockMvc.perform(get("/api/insurance-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].policiesCategory").value(hasItem(DEFAULT_POLICIES_CATEGORY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].applicationGenKey").value(hasItem(DEFAULT_APPLICATION_GEN_KEY)));
    }
    
    @Test
    @Transactional
    public void getInsuranceForm() throws Exception {
        // Initialize the database
        insuranceFormRepository.saveAndFlush(insuranceForm);

        // Get the insuranceForm
        restInsuranceFormMockMvc.perform(get("/api/insurance-forms/{id}", insuranceForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceForm.getId().intValue()))
            .andExpect(jsonPath("$.policiesCategory").value(DEFAULT_POLICIES_CATEGORY))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.applicationGenKey").value(DEFAULT_APPLICATION_GEN_KEY));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceForm() throws Exception {
        // Get the insuranceForm
        restInsuranceFormMockMvc.perform(get("/api/insurance-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceForm() throws Exception {
        // Initialize the database
        insuranceFormRepository.saveAndFlush(insuranceForm);

        int databaseSizeBeforeUpdate = insuranceFormRepository.findAll().size();

        // Update the insuranceForm
        InsuranceForm updatedInsuranceForm = insuranceFormRepository.findById(insuranceForm.getId()).get();
        // Disconnect from session so that the updates on updatedInsuranceForm are not directly saved in db
        em.detach(updatedInsuranceForm);
        updatedInsuranceForm
            .policiesCategory(UPDATED_POLICIES_CATEGORY)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .pincode(UPDATED_PINCODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .applicationGenKey(UPDATED_APPLICATION_GEN_KEY);

        restInsuranceFormMockMvc.perform(put("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceForm)))
            .andExpect(status().isOk());

        // Validate the InsuranceForm in the database
        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeUpdate);
        InsuranceForm testInsuranceForm = insuranceFormList.get(insuranceFormList.size() - 1);
        assertThat(testInsuranceForm.getPoliciesCategory()).isEqualTo(UPDATED_POLICIES_CATEGORY);
        assertThat(testInsuranceForm.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testInsuranceForm.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testInsuranceForm.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testInsuranceForm.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInsuranceForm.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testInsuranceForm.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testInsuranceForm.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testInsuranceForm.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testInsuranceForm.getApplicationGenKey()).isEqualTo(UPDATED_APPLICATION_GEN_KEY);

        // Validate the InsuranceForm in Elasticsearch
        verify(mockInsuranceFormSearchRepository, times(1)).save(testInsuranceForm);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceForm() throws Exception {
        int databaseSizeBeforeUpdate = insuranceFormRepository.findAll().size();

        // Create the InsuranceForm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceFormMockMvc.perform(put("/api/insurance-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceForm)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceForm in the database
        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InsuranceForm in Elasticsearch
        verify(mockInsuranceFormSearchRepository, times(0)).save(insuranceForm);
    }

    @Test
    @Transactional
    public void deleteInsuranceForm() throws Exception {
        // Initialize the database
        insuranceFormRepository.saveAndFlush(insuranceForm);

        int databaseSizeBeforeDelete = insuranceFormRepository.findAll().size();

        // Delete the insuranceForm
        restInsuranceFormMockMvc.perform(delete("/api/insurance-forms/{id}", insuranceForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InsuranceForm> insuranceFormList = insuranceFormRepository.findAll();
        assertThat(insuranceFormList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InsuranceForm in Elasticsearch
        verify(mockInsuranceFormSearchRepository, times(1)).deleteById(insuranceForm.getId());
    }

    @Test
    @Transactional
    public void searchInsuranceForm() throws Exception {
        // Initialize the database
        insuranceFormRepository.saveAndFlush(insuranceForm);
        when(mockInsuranceFormSearchRepository.search(queryStringQuery("id:" + insuranceForm.getId())))
            .thenReturn(Collections.singletonList(insuranceForm));
        // Search the insuranceForm
        restInsuranceFormMockMvc.perform(get("/api/_search/insurance-forms?query=id:" + insuranceForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].policiesCategory").value(hasItem(DEFAULT_POLICIES_CATEGORY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].applicationGenKey").value(hasItem(DEFAULT_APPLICATION_GEN_KEY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceForm.class);
        InsuranceForm insuranceForm1 = new InsuranceForm();
        insuranceForm1.setId(1L);
        InsuranceForm insuranceForm2 = new InsuranceForm();
        insuranceForm2.setId(insuranceForm1.getId());
        assertThat(insuranceForm1).isEqualTo(insuranceForm2);
        insuranceForm2.setId(2L);
        assertThat(insuranceForm1).isNotEqualTo(insuranceForm2);
        insuranceForm1.setId(null);
        assertThat(insuranceForm1).isNotEqualTo(insuranceForm2);
    }
}
