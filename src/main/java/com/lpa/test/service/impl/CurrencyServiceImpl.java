package com.lpa.test.service.impl;

import com.lpa.test.entity.Currency;
import com.lpa.test.repository.CurrencyRepository;
import com.lpa.test.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency getCurrency(String code){
        Currency currency = currencyRepository.findCurrencyByCode(code);
        return currency;
    }

    @Override
    public List<Currency> findAll(){
        List<Currency> currencies = currencyRepository.findAll();
        return currencies;
    }
}
