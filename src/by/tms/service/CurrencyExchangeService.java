package by.tms.service;

import by.tms.entity.Rate;

import java.util.List;

public interface CurrencyExchangeService {
    boolean addNewExchangeRate(List<String> options);

    boolean removeExistingRate(List<String> inputRate);

    List<Rate> getListExchangeRates(List<String> inputRate);
}
