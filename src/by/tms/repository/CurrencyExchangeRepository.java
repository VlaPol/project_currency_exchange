package by.tms.repository;

import by.tms.entity.Rate;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface CurrencyExchangeRepository {
    void saveRateToFile(LocalDate currentDate, Rate currentCurrencyRate);
    Map<Currency,Rate> getRatesFromFile(LocalDate currencyRatesDate);

    void removeRateFromFile(Rate rate);
}
