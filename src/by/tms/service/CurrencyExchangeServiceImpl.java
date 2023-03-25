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
        Rate newRate = new Rate();
        newRate.setCurrencyCode(Currency.getInstance(incomingRateInList.get(1)));
        newRate.setSellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(2))));
        newRate.setBuyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(3))));
        repository.saveRateToFile(LocalDate.parse(incomingRateInList.get(0)), newRate);
    }

    @Override
    public void removeExistingRate(List<String> inputRate) {

        Map<Currency,Rate> existingRatesList = repository.getRatesFromFile(LocalDate.parse(inputRate.get(0)));

        if(existingRatesList.containsKey(Currency.getInstance(inputRate.get(1)))) {
            // repository.removeRate
        }
    }
}
