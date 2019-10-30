package com.lpa.test.service;
import com.lpa.test.entity.Currency;


import java.util.List;


public interface CurrencyService {
    Currency getCurrency(String code);

    List<Currency> findAll();
}
