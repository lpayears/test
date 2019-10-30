package com.lpa.test.service;

import com.lpa.test.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> findAll();

    Country getCountry(String code);

}