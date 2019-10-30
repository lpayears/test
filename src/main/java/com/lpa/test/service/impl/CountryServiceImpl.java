package com.lpa.test.service.impl;

import com.lpa.test.entity.Country;
import com.lpa.test.repository.CountryRepository;
import com.lpa.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll(){return  countryRepository.findAll();}


    @Override
    public Country getCountry(String code){
        Country country = countryRepository.findCountryByCode(code);
        return country;
    }
}
