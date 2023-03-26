package by.tms.service;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyExchangeService {
    boolean addNewExchangeRate(List<String> options);

    boolean removeExistingRate(List<String> inputRate);

    List<String> getListExchangeRates(List<String> inputRate);

    BigDecimal exchangeCurrencies(List<String> inputRate);
}
