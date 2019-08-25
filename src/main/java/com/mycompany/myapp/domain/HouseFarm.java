package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A HouseFarm.
 */
@Entity
@Table(name = "house_farm")
public class HouseFarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "has_license")
    private Boolean hasLicense;

    @Column(name = "date_founded")
    private LocalDate dateFounded;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "province")
    private String province;

    @JsonIgnoreProperties(value = {"houseFarm"}, allowSetters = true)
    @OneToMany(mappedBy = "houseFarm", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @JsonIgnoreProperties(value = {"houseFarm"}, allowSetters = true)
    @OneToMany(mappedBy = "houseFarm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> orders = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

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

    public HouseFarm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isHasLicense() {
        return hasLicense;
    }

    public HouseFarm hasLicense(Boolean hasLicense) {
        this.hasLicense = hasLicense;
        return this;
    }

    public void setHasLicense(Boolean hasLicense) {
        this.hasLicense = hasLicense;
    }

    public LocalDate getDateFounded() {
        return dateFounded;
    }

    public HouseFarm dateFounded(LocalDate dateFounded) {
        this.dateFounded = dateFounded;
        return this;
    }

    public void setDateFounded(LocalDate dateFounded) {
        this.dateFounded = dateFounded;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public HouseFarm contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public HouseFarm products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public HouseFarm addProduct(Product product) {
        this.products.add(product);
        product.setHouseFarm(this);
        return this;
    }

    public HouseFarm removeProduct(Product product) {
        this.products.remove(product);
        product.setHouseFarm(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public HouseFarm orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public HouseFarm addOrder(Order order) {
        this.orders.add(order);
        order.setHouseFarm(this);
        return this;
    }

    public HouseFarm removeOrder(Order order) {
        this.orders.remove(order);
        order.setHouseFarm(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseFarm)) {
            return false;
        }
        return id != null && id.equals(((HouseFarm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HouseFarm{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hasLicense='" + isHasLicense() + "'" +
            ", dateFounded='" + getDateFounded() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            "}";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getHasLicense() {
        return hasLicense;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
