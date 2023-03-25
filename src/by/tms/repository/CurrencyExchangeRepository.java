package by.tms.repository;

import by.tms.entity.Rate;

import java.util.List;

public interface CurrencyExchangeRepository {
    void saveRateToFile(Rate currentCurrencyRate);
    List<Rate> getRatesFromFile(String currencyRatesDate);
}
