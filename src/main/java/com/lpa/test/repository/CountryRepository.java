package com.lpa.test.repository;

import com.lpa.test.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
    Country findCountryByCode(String code);
}
