package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ProductType;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Product} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {

        public ProductTypeFilter() {
        }

        public ProductTypeFilter(ProductTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProductTypeFilter copy() {
            return new ProductTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter imageUrl;

    private DoubleFilter price;

    private DoubleFilter availableAmountInLiters;

    private DoubleFilter availableAmountInKilograms;

    private BooleanFilter isAvailable;

    private ProductTypeFilter productType;

    private LongFilter ratingId;

    private LongFilter houseFarmId;

    private LongFilter orderId;

    public ProductCriteria(){
    }

    public ProductCriteria(ProductCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.availableAmountInLiters = other.availableAmountInLiters == null ? null : other.availableAmountInLiters.copy();
        this.availableAmountInKilograms = other.availableAmountInKilograms == null ? null : other.availableAmountInKilograms.copy();
        this.isAvailable = other.isAvailable == null ? null : other.isAvailable.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.houseFarmId = other.houseFarmId == null ? null : other.houseFarmId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public DoubleFilter getAvailableAmountInLiters() {
        return availableAmountInLiters;
    }

    public void setAvailableAmountInLiters(DoubleFilter availableAmountInLiters) {
        this.availableAmountInLiters = availableAmountInLiters;
    }

    public DoubleFilter getAvailableAmountInKilograms() {
        return availableAmountInKilograms;
    }

    public void setAvailableAmountInKilograms(DoubleFilter availableAmountInKilograms) {
        this.availableAmountInKilograms = availableAmountInKilograms;
    }

    public BooleanFilter getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(BooleanFilter isAvailable) {
        this.isAvailable = isAvailable;
    }

    public ProductTypeFilter getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeFilter productType) {
        this.productType = productType;
    }

    public LongFilter getRatingId() {
        return ratingId;
    }

    public void setRatingId(LongFilter ratingId) {
        this.ratingId = ratingId;
    }

    public LongFilter getHouseFarmId() {
        return houseFarmId;
    }

    public void setHouseFarmId(LongFilter houseFarmId) {
        this.houseFarmId = houseFarmId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(price, that.price) &&
            Objects.equals(availableAmountInLiters, that.availableAmountInLiters) &&
            Objects.equals(availableAmountInKilograms, that.availableAmountInKilograms) &&
            Objects.equals(isAvailable, that.isAvailable) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(houseFarmId, that.houseFarmId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        imageUrl,
        price,
        availableAmountInLiters,
        availableAmountInKilograms,
        isAvailable,
        productType,
        ratingId,
        houseFarmId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (availableAmountInLiters != null ? "availableAmountInLiters=" + availableAmountInLiters + ", " : "") +
                (availableAmountInKilograms != null ? "availableAmountInKilograms=" + availableAmountInKilograms + ", " : "") +
                (isAvailable != null ? "isAvailable=" + isAvailable + ", " : "") +
                (productType != null ? "productType=" + productType + ", " : "") +
                (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
                (houseFarmId != null ? "houseFarmId=" + houseFarmId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
