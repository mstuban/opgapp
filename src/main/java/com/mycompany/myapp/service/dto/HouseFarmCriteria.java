package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.HouseFarm} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HouseFarmResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /house-farms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HouseFarmCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter hasLicense;

    private LocalDateFilter dateFounded;

    private StringFilter contactNumber;

    private LongFilter locationId;

    private LongFilter productId;

    private LongFilter orderId;

    public HouseFarmCriteria(){
    }

    public HouseFarmCriteria(HouseFarmCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.hasLicense = other.hasLicense == null ? null : other.hasLicense.copy();
        this.dateFounded = other.dateFounded == null ? null : other.dateFounded.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public HouseFarmCriteria copy() {
        return new HouseFarmCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getHasLicense() {
        return hasLicense;
    }

    public void setHasLicense(BooleanFilter hasLicense) {
        this.hasLicense = hasLicense;
    }

    public LocalDateFilter getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(LocalDateFilter dateFounded) {
        this.dateFounded = dateFounded;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HouseFarmCriteria that = (HouseFarmCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(hasLicense, that.hasLicense) &&
            Objects.equals(dateFounded, that.dateFounded) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        hasLicense,
        dateFounded,
        contactNumber,
        locationId,
        productId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "HouseFarmCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (hasLicense != null ? "hasLicense=" + hasLicense + ", " : "") +
                (dateFounded != null ? "dateFounded=" + dateFounded + ", " : "") +
                (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
