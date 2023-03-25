package by.tms.service;

import by.tms.entity.Rate;

import java.util.List;

public interface CurrencyExchangeService {
    void addNewExchangeRate(List<String> options);

    void removeExistingRate(List<String> inputRate);

    List<Rate> getListExchangeRates(List<String> inputRate);
}
