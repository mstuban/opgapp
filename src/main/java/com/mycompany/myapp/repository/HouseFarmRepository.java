package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HouseFarm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HouseFarm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseFarmRepository extends JpaRepository<HouseFarm, Long> {

}
