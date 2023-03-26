package by.tms.repository;

import by.tms.entity.Rate;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

public interface CurrencyExchangeRepository {
    boolean saveRateToFile(LocalDate currentDate, Rate currentCurrencyRate);

    Map<Currency, String> getRatesFromFile(LocalDate currencyRatesDate);

    boolean removeRateFromFile(LocalDate date, Currency deletedCurrency);
}
