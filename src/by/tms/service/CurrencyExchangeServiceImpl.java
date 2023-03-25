package by.tms.service;

import by.tms.entity.Rate;
import by.tms.repository.CurrencyExchangeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeRepository repository;

    public CurrencyExchangeServiceImpl(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addNewExchangeRate(List<String> incomingRateInList) {
        Rate newRate = new Rate.Builder()
                .currencyCode(Currency.getInstance(incomingRateInList.get(1)))
                .sellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(2))))
                .buyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(3))))
                .build();

        repository.saveRateToFile(LocalDate.parse(incomingRateInList.get(0)), newRate);
    }

    @Override
    public void removeExistingRate(List<String> inputRate) {

        LocalDate date = LocalDate.parse(inputRate.get(0));
        Map<Currency,Rate> existingRatesList = repository.getRatesFromFile(date);

        if(existingRatesList.containsKey(Currency.getInstance(inputRate.get(1)))) {
            repository.removeRateFromFile(date, Currency.getInstance(inputRate.get(1)));
        }
    }
}
