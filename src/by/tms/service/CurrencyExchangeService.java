package by.tms.service;

import java.util.List;

public interface CurrencyExchangeService {
    void addNewExchangeRate(List<String> options);

    void removeExistingRate(List<String> inputRate);
}
