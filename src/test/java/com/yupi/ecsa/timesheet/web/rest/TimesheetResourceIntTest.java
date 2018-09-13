package com.yupi.ecsa.timesheet.web.rest;

import com.yupi.ecsa.timesheet.TimesheetApp;

import com.yupi.ecsa.timesheet.domain.Timesheet;
import com.yupi.ecsa.timesheet.repository.TimesheetRepository;
import com.yupi.ecsa.timesheet.service.TimesheetService;
import com.yupi.ecsa.timesheet.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.yupi.ecsa.timesheet.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimesheetResource REST controller.
 *
 * @see TimesheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimesheetApp.class)
public class TimesheetResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_AMOUNT = new BigDecimal(2);

    private static final Long DEFAULT_ODOO_ID = 1L;
    private static final Long UPDATED_ODOO_ID = 2L;

    @Autowired
    private TimesheetRepository timesheetRepository;
    
    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimesheetMockMvc;

    private Timesheet timesheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimesheetResource timesheetResource = new TimesheetResource(timesheetService);
        this.restTimesheetMockMvc = MockMvcBuilders.standaloneSetup(timesheetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timesheet createEntity(EntityManager em) {
        Timesheet timesheet = new Timesheet()
            .date(DEFAULT_DATE)
            .name(DEFAULT_NAME)
            .unitAmount(DEFAULT_UNIT_AMOUNT)
            .odooId(DEFAULT_ODOO_ID);
        return timesheet;
    }

    @Before
    public void initTest() {
        timesheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimesheet() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isCreated());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate + 1);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimesheet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTimesheet.getUnitAmount()).isEqualTo(DEFAULT_UNIT_AMOUNT);
        assertThat(testTimesheet.getOdooId()).isEqualTo(DEFAULT_ODOO_ID);
    }

    @Test
    @Transactional
    public void createTimesheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timesheetRepository.findAll().size();

        // Create the Timesheet with an existing ID
        timesheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setDate(null);

        // Create the Timesheet, which fails.

        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setName(null);

        // Create the Timesheet, which fails.

        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = timesheetRepository.findAll().size();
        // set the field null
        timesheet.setUnitAmount(null);

        // Create the Timesheet, which fails.

        restTimesheetMockMvc.perform(post("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isBadRequest());

        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimesheets() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get all the timesheetList
        restTimesheetMockMvc.perform(get("/api/timesheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].unitAmount").value(hasItem(DEFAULT_UNIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].odooId").value(hasItem(DEFAULT_ODOO_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTimesheet() throws Exception {
        // Initialize the database
        timesheetRepository.saveAndFlush(timesheet);

        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", timesheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timesheet.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.unitAmount").value(DEFAULT_UNIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.odooId").value(DEFAULT_ODOO_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimesheet() throws Exception {
        // Get the timesheet
        restTimesheetMockMvc.perform(get("/api/timesheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimesheet() throws Exception {
        // Initialize the database
        timesheetService.save(timesheet);

        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Update the timesheet
        Timesheet updatedTimesheet = timesheetRepository.findById(timesheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimesheet are not directly saved in db
        em.detach(updatedTimesheet);
        updatedTimesheet
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .odooId(UPDATED_ODOO_ID);

        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimesheet)))
            .andExpect(status().isOk());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
        Timesheet testTimesheet = timesheetList.get(timesheetList.size() - 1);
        assertThat(testTimesheet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimesheet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTimesheet.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testTimesheet.getOdooId()).isEqualTo(UPDATED_ODOO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTimesheet() throws Exception {
        int databaseSizeBeforeUpdate = timesheetRepository.findAll().size();

        // Create the Timesheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimesheetMockMvc.perform(put("/api/timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timesheet)))
            .andExpect(status().isBadRequest());

        // Validate the Timesheet in the database
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimesheet() throws Exception {
        // Initialize the database
        timesheetService.save(timesheet);

        int databaseSizeBeforeDelete = timesheetRepository.findAll().size();

        // Get the timesheet
        restTimesheetMockMvc.perform(delete("/api/timesheets/{id}", timesheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timesheet> timesheetList = timesheetRepository.findAll();
        assertThat(timesheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timesheet.class);
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setId(1L);
        Timesheet timesheet2 = new Timesheet();
        timesheet2.setId(timesheet1.getId());
        assertThat(timesheet1).isEqualTo(timesheet2);
        timesheet2.setId(2L);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
        timesheet1.setId(null);
        assertThat(timesheet1).isNotEqualTo(timesheet2);
    }
}
