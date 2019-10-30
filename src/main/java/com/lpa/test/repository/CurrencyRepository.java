package com.lpa.test.repository;

import com.lpa.test.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository  extends JpaRepository<Currency,Integer> {

    Currency findCurrencyByCode(String Code);
}
