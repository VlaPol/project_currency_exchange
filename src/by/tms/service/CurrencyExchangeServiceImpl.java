package by.tms.service;

import by.tms.repository.CurrencyExchangeRepository;

public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    CurrencyExchangeRepository repository;

    public CurrencyExchangeServiceImpl(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }
}
