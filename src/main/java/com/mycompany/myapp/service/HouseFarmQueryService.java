package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.HouseFarm;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.HouseFarmRepository;
import com.mycompany.myapp.service.dto.HouseFarmCriteria;

/**
 * Service for executing complex queries for {@link HouseFarm} entities in the database.
 * The main input is a {@link HouseFarmCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HouseFarm} or a {@link Page} of {@link HouseFarm} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HouseFarmQueryService extends QueryService<HouseFarm> {

    private final Logger log = LoggerFactory.getLogger(HouseFarmQueryService.class);

    private final HouseFarmRepository houseFarmRepository;

    public HouseFarmQueryService(HouseFarmRepository houseFarmRepository) {
        this.houseFarmRepository = houseFarmRepository;
    }

    /**
     * Return a {@link List} of {@link HouseFarm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HouseFarm> findByCriteria(HouseFarmCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HouseFarm> specification = createSpecification(criteria);
        return houseFarmRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HouseFarm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HouseFarm> findByCriteria(HouseFarmCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HouseFarm> specification = createSpecification(criteria);
        return houseFarmRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HouseFarmCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HouseFarm> specification = createSpecification(criteria);
        return houseFarmRepository.count(specification);
    }

    /**
     * Function to convert HouseFarmCriteria to a {@link Specification}.
     */
    private Specification<HouseFarm> createSpecification(HouseFarmCriteria criteria) {
        Specification<HouseFarm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HouseFarm_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), HouseFarm_.name));
            }
            if (criteria.getHasLicense() != null) {
                specification = specification.and(buildSpecification(criteria.getHasLicense(), HouseFarm_.hasLicense));
            }
            if (criteria.getDateFounded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFounded(), HouseFarm_.dateFounded));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), HouseFarm_.contactNumber));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(HouseFarm_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(HouseFarm_.orders, JoinType.LEFT).get(Order_.id)));
            }
        }
        return specification;
    }
}
