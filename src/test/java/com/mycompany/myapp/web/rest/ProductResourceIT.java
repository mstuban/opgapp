package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OpgappApp;
import com.mycompany.myapp.domain.HouseFarm;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.Rating;
import com.mycompany.myapp.domain.enumeration.ProductType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.ProductQueryService;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@Link ProductResource} REST controller.
 */
@SpringBootTest(classes = OpgappApp.class)
public class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Double DEFAULT_AVAILABLE_AMOUNT_IN_LITERS = 1D;
    private static final Double UPDATED_AVAILABLE_AMOUNT_IN_LITERS = 2D;

    private static final Double DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS = 1D;
    private static final Double UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS = 2D;

    private static final Boolean DEFAULT_IS_AVAILABLE = false;
    private static final Boolean UPDATED_IS_AVAILABLE = true;

    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.MEAT;
    private static final ProductType UPDATED_PRODUCT_TYPE = ProductType.CHEESE;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private UserRepository userRepository;

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

    private MockMvc restProductMockMvc;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productRepository, userRepository,
            productQueryService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
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
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .price(DEFAULT_PRICE)
            .availableAmountInLiters(DEFAULT_AVAILABLE_AMOUNT_IN_LITERS)
            .availableAmountInKilograms(DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS)
            .isAvailable(DEFAULT_IS_AVAILABLE)
            .productType(DEFAULT_PRODUCT_TYPE);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .price(UPDATED_PRICE)
            .availableAmountInLiters(UPDATED_AVAILABLE_AMOUNT_IN_LITERS)
            .availableAmountInKilograms(UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .productType(UPDATED_PRODUCT_TYPE);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getAvailableAmountInLiters()).isEqualTo(DEFAULT_AVAILABLE_AMOUNT_IN_LITERS);
        assertThat(testProduct.getAvailableAmountInKilograms()).isEqualTo(DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS);
        assertThat(testProduct.isIsAvailable()).isEqualTo(DEFAULT_IS_AVAILABLE);
        assertThat(testProduct.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].availableAmountInLiters").value(hasItem(DEFAULT_AVAILABLE_AMOUNT_IN_LITERS.doubleValue())))
            .andExpect(jsonPath("$.[*].availableAmountInKilograms").value(hasItem(DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS.doubleValue())))
            .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.availableAmountInLiters").value(DEFAULT_AVAILABLE_AMOUNT_IN_LITERS.doubleValue()))
            .andExpect(jsonPath("$.availableAmountInKilograms").value(DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS.doubleValue()))
            .andExpect(jsonPath("$.isAvailable").value(DEFAULT_IS_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name equals to DEFAULT_NAME
        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name is not null
        defaultProductShouldBeFound("name.specified=true");

        // Get all the productList where name is null
        defaultProductShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the productList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultProductShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the productList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where imageUrl is not null
        defaultProductShouldBeFound("imageUrl.specified=true");

        // Get all the productList where imageUrl is null
        defaultProductShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price equals to DEFAULT_PRICE
        defaultProductShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is not null
        defaultProductShouldBeFound("price.specified=true");

        // Get all the productList where price is null
        defaultProductShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInLitersIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInLiters equals to DEFAULT_AVAILABLE_AMOUNT_IN_LITERS
        defaultProductShouldBeFound("availableAmountInLiters.equals=" + DEFAULT_AVAILABLE_AMOUNT_IN_LITERS);

        // Get all the productList where availableAmountInLiters equals to UPDATED_AVAILABLE_AMOUNT_IN_LITERS
        defaultProductShouldNotBeFound("availableAmountInLiters.equals=" + UPDATED_AVAILABLE_AMOUNT_IN_LITERS);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInLitersIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInLiters in DEFAULT_AVAILABLE_AMOUNT_IN_LITERS or UPDATED_AVAILABLE_AMOUNT_IN_LITERS
        defaultProductShouldBeFound("availableAmountInLiters.in=" + DEFAULT_AVAILABLE_AMOUNT_IN_LITERS + "," + UPDATED_AVAILABLE_AMOUNT_IN_LITERS);

        // Get all the productList where availableAmountInLiters equals to UPDATED_AVAILABLE_AMOUNT_IN_LITERS
        defaultProductShouldNotBeFound("availableAmountInLiters.in=" + UPDATED_AVAILABLE_AMOUNT_IN_LITERS);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInLitersIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInLiters is not null
        defaultProductShouldBeFound("availableAmountInLiters.specified=true");

        // Get all the productList where availableAmountInLiters is null
        defaultProductShouldNotBeFound("availableAmountInLiters.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInKilogramsIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInKilograms equals to DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS
        defaultProductShouldBeFound("availableAmountInKilograms.equals=" + DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS);

        // Get all the productList where availableAmountInKilograms equals to UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS
        defaultProductShouldNotBeFound("availableAmountInKilograms.equals=" + UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInKilogramsIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInKilograms in DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS or UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS
        defaultProductShouldBeFound("availableAmountInKilograms.in=" + DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS + "," + UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS);

        // Get all the productList where availableAmountInKilograms equals to UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS
        defaultProductShouldNotBeFound("availableAmountInKilograms.in=" + UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableAmountInKilogramsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where availableAmountInKilograms is not null
        defaultProductShouldBeFound("availableAmountInKilograms.specified=true");

        // Get all the productList where availableAmountInKilograms is null
        defaultProductShouldNotBeFound("availableAmountInKilograms.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIsAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isAvailable equals to DEFAULT_IS_AVAILABLE
        defaultProductShouldBeFound("isAvailable.equals=" + DEFAULT_IS_AVAILABLE);

        // Get all the productList where isAvailable equals to UPDATED_IS_AVAILABLE
        defaultProductShouldNotBeFound("isAvailable.equals=" + UPDATED_IS_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllProductsByIsAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isAvailable in DEFAULT_IS_AVAILABLE or UPDATED_IS_AVAILABLE
        defaultProductShouldBeFound("isAvailable.in=" + DEFAULT_IS_AVAILABLE + "," + UPDATED_IS_AVAILABLE);

        // Get all the productList where isAvailable equals to UPDATED_IS_AVAILABLE
        defaultProductShouldNotBeFound("isAvailable.in=" + UPDATED_IS_AVAILABLE);
    }

    @Test
    @Transactional
    public void getAllProductsByIsAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isAvailable is not null
        defaultProductShouldBeFound("isAvailable.specified=true");

        // Get all the productList where isAvailable is null
        defaultProductShouldNotBeFound("isAvailable.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultProductShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultProductShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the productList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productType is not null
        defaultProductShouldBeFound("productType.specified=true");

        // Get all the productList where productType is null
        defaultProductShouldNotBeFound("productType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        Rating rating = RatingResourceIT.createEntity(em);
        em.persist(rating);
        em.flush();
        product.addRating(rating);
        productRepository.saveAndFlush(product);
        Long ratingId = rating.getId();

        // Get all the productList where rating equals to ratingId
        defaultProductShouldBeFound("ratingId.equals=" + ratingId);

        // Get all the productList where rating equals to ratingId + 1
        defaultProductShouldNotBeFound("ratingId.equals=" + (ratingId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByHouseFarmIsEqualToSomething() throws Exception {
        // Initialize the database
        HouseFarm houseFarm = HouseFarmResourceIT.createEntity(em);
        em.persist(houseFarm);
        em.flush();
        product.setHouseFarm(houseFarm);
        productRepository.saveAndFlush(product);
        Long houseFarmId = houseFarm.getId();

        // Get all the productList where houseFarm equals to houseFarmId
        defaultProductShouldBeFound("houseFarmId.equals=" + houseFarmId);

        // Get all the productList where houseFarm equals to houseFarmId + 1
        defaultProductShouldNotBeFound("houseFarmId.equals=" + (houseFarmId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        product.setOrder(order);
        productRepository.saveAndFlush(product);
        Long orderId = order.getId();

        // Get all the productList where order equals to orderId
        defaultProductShouldBeFound("orderId.equals=" + orderId);

        // Get all the productList where order equals to orderId + 1
        defaultProductShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].availableAmountInLiters").value(hasItem(DEFAULT_AVAILABLE_AMOUNT_IN_LITERS.doubleValue())))
            .andExpect(jsonPath("$.[*].availableAmountInKilograms").value(hasItem(DEFAULT_AVAILABLE_AMOUNT_IN_KILOGRAMS.doubleValue())))
            .andExpect(jsonPath("$.[*].isAvailable").value(hasItem(DEFAULT_IS_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())));

        // Check, that the count call also returns 1
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .price(UPDATED_PRICE)
            .availableAmountInLiters(UPDATED_AVAILABLE_AMOUNT_IN_LITERS)
            .availableAmountInKilograms(UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS)
            .isAvailable(UPDATED_IS_AVAILABLE)
            .productType(UPDATED_PRODUCT_TYPE);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getAvailableAmountInLiters()).isEqualTo(UPDATED_AVAILABLE_AMOUNT_IN_LITERS);
        assertThat(testProduct.getAvailableAmountInKilograms()).isEqualTo(UPDATED_AVAILABLE_AMOUNT_IN_KILOGRAMS);
        assertThat(testProduct.isIsAvailable()).isEqualTo(UPDATED_IS_AVAILABLE);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }
}
