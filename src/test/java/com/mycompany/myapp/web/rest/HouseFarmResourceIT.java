package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpgappApp;
import com.mycompany.myapp.domain.HouseFarm;
import com.mycompany.myapp.repository.HouseFarmRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link HouseFarmResource} REST controller.
 */
@SpringBootTest(classes = OpgappApp.class)
public class HouseFarmResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_LICENSE = false;
    private static final Boolean UPDATED_HAS_LICENSE = true;

    private static final LocalDate DEFAULT_DATE_FOUNDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FOUNDED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private HouseFarmRepository houseFarmRepository;

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

    private MockMvc restHouseFarmMockMvc;

    private HouseFarm houseFarm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HouseFarmResource houseFarmResource = new HouseFarmResource(houseFarmRepository);
        this.restHouseFarmMockMvc = MockMvcBuilders.standaloneSetup(houseFarmResource)
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
    public static HouseFarm createEntity(EntityManager em) {
        HouseFarm houseFarm = new HouseFarm()
            .name(DEFAULT_NAME)
            .hasLicense(DEFAULT_HAS_LICENSE)
            .dateFounded(DEFAULT_DATE_FOUNDED)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return houseFarm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HouseFarm createUpdatedEntity(EntityManager em) {
        HouseFarm houseFarm = new HouseFarm()
            .name(UPDATED_NAME)
            .hasLicense(UPDATED_HAS_LICENSE)
            .dateFounded(UPDATED_DATE_FOUNDED)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        return houseFarm;
    }

    @BeforeEach
    public void initTest() {
        houseFarm = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouseFarm() throws Exception {
        int databaseSizeBeforeCreate = houseFarmRepository.findAll().size();

        // Create the HouseFarm
        restHouseFarmMockMvc.perform(post("/api/house-farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseFarm)))
            .andExpect(status().isCreated());

        // Validate the HouseFarm in the database
        List<HouseFarm> houseFarmList = houseFarmRepository.findAll();
        assertThat(houseFarmList).hasSize(databaseSizeBeforeCreate + 1);
        HouseFarm testHouseFarm = houseFarmList.get(houseFarmList.size() - 1);
        assertThat(testHouseFarm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHouseFarm.isHasLicense()).isEqualTo(DEFAULT_HAS_LICENSE);
        assertThat(testHouseFarm.getDateFounded()).isEqualTo(DEFAULT_DATE_FOUNDED);
        assertThat(testHouseFarm.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void createHouseFarmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = houseFarmRepository.findAll().size();

        // Create the HouseFarm with an existing ID
        houseFarm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseFarmMockMvc.perform(post("/api/house-farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseFarm)))
            .andExpect(status().isBadRequest());

        // Validate the HouseFarm in the database
        List<HouseFarm> houseFarmList = houseFarmRepository.findAll();
        assertThat(houseFarmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHouseFarms() throws Exception {
        // Initialize the database
        houseFarmRepository.saveAndFlush(houseFarm);

        // Get all the houseFarmList
        restHouseFarmMockMvc.perform(get("/api/house-farms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(houseFarm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hasLicense").value(hasItem(DEFAULT_HAS_LICENSE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateFounded").value(hasItem(DEFAULT_DATE_FOUNDED.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getHouseFarm() throws Exception {
        // Initialize the database
        houseFarmRepository.saveAndFlush(houseFarm);

        // Get the houseFarm
        restHouseFarmMockMvc.perform(get("/api/house-farms/{id}", houseFarm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(houseFarm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.hasLicense").value(DEFAULT_HAS_LICENSE.booleanValue()))
            .andExpect(jsonPath("$.dateFounded").value(DEFAULT_DATE_FOUNDED.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHouseFarm() throws Exception {
        // Get the houseFarm
        restHouseFarmMockMvc.perform(get("/api/house-farms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouseFarm() throws Exception {
        // Initialize the database
        houseFarmRepository.saveAndFlush(houseFarm);

        int databaseSizeBeforeUpdate = houseFarmRepository.findAll().size();

        // Update the houseFarm
        HouseFarm updatedHouseFarm = houseFarmRepository.findById(houseFarm.getId()).get();
        // Disconnect from session so that the updates on updatedHouseFarm are not directly saved in db
        em.detach(updatedHouseFarm);
        updatedHouseFarm
            .name(UPDATED_NAME)
            .hasLicense(UPDATED_HAS_LICENSE)
            .dateFounded(UPDATED_DATE_FOUNDED)
            .contactNumber(UPDATED_CONTACT_NUMBER);

        restHouseFarmMockMvc.perform(put("/api/house-farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHouseFarm)))
            .andExpect(status().isOk());

        // Validate the HouseFarm in the database
        List<HouseFarm> houseFarmList = houseFarmRepository.findAll();
        assertThat(houseFarmList).hasSize(databaseSizeBeforeUpdate);
        HouseFarm testHouseFarm = houseFarmList.get(houseFarmList.size() - 1);
        assertThat(testHouseFarm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHouseFarm.isHasLicense()).isEqualTo(UPDATED_HAS_LICENSE);
        assertThat(testHouseFarm.getDateFounded()).isEqualTo(UPDATED_DATE_FOUNDED);
        assertThat(testHouseFarm.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingHouseFarm() throws Exception {
        int databaseSizeBeforeUpdate = houseFarmRepository.findAll().size();

        // Create the HouseFarm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseFarmMockMvc.perform(put("/api/house-farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(houseFarm)))
            .andExpect(status().isBadRequest());

        // Validate the HouseFarm in the database
        List<HouseFarm> houseFarmList = houseFarmRepository.findAll();
        assertThat(houseFarmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHouseFarm() throws Exception {
        // Initialize the database
        houseFarmRepository.saveAndFlush(houseFarm);

        int databaseSizeBeforeDelete = houseFarmRepository.findAll().size();

        // Delete the houseFarm
        restHouseFarmMockMvc.perform(delete("/api/house-farms/{id}", houseFarm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<HouseFarm> houseFarmList = houseFarmRepository.findAll();
        assertThat(houseFarmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseFarm.class);
        HouseFarm houseFarm1 = new HouseFarm();
        houseFarm1.setId(1L);
        HouseFarm houseFarm2 = new HouseFarm();
        houseFarm2.setId(houseFarm1.getId());
        assertThat(houseFarm1).isEqualTo(houseFarm2);
        houseFarm2.setId(2L);
        assertThat(houseFarm1).isNotEqualTo(houseFarm2);
        houseFarm1.setId(null);
        assertThat(houseFarm1).isNotEqualTo(houseFarm2);
    }
}
