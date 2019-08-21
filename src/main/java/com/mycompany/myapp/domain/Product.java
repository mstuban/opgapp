package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.ProductType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private Double price;

    @Column(name = "available_amount_in_liters")
    private Double availableAmountInLiters;

    @Column(name = "available_amount_in_kilograms")
    private Double availableAmountInKilograms;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private HouseFarm houseFarm;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAvailableAmountInLiters() {
        return availableAmountInLiters;
    }

    public Product availableAmountInLiters(Double availableAmountInLiters) {
        this.availableAmountInLiters = availableAmountInLiters;
        return this;
    }

    public void setAvailableAmountInLiters(Double availableAmountInLiters) {
        this.availableAmountInLiters = availableAmountInLiters;
    }

    public Double getAvailableAmountInKilograms() {
        return availableAmountInKilograms;
    }

    public Product availableAmountInKilograms(Double availableAmountInKilograms) {
        this.availableAmountInKilograms = availableAmountInKilograms;
        return this;
    }

    public void setAvailableAmountInKilograms(Double availableAmountInKilograms) {
        this.availableAmountInKilograms = availableAmountInKilograms;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public Product isAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Product ratings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Product addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setProduct(this);
        return this;
    }

    public Product removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setProduct(null);
        return this;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public HouseFarm getHouseFarm() {
        return houseFarm;
    }

    public Product houseFarm(HouseFarm houseFarm) {
        this.houseFarm = houseFarm;
        return this;
    }

    public void setHouseFarm(HouseFarm houseFarm) {
        this.houseFarm = houseFarm;
    }

    public Order getOrder() {
        return order;
    }

    public Product order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", price=" + getPrice() +
            ", availableAmountInLiters=" + getAvailableAmountInLiters() +
            ", availableAmountInKilograms=" + getAvailableAmountInKilograms() +
            ", isAvailable='" + isIsAvailable() + "'" +
            ", productType='" + getProductType() + "'" +
            "}";
    }
}
