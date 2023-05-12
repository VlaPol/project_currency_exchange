package by.tms.service;

import by.tms.entity.Rate;
import by.tms.repository.CurrencyExchangeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeRepository repository;

    public CurrencyExchangeServiceImpl(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addNewExchangeRate(List<String> incomingRateInList) {
        Rate newRate = new Rate.Builder()
                .currencyCode(Currency.getInstance(incomingRateInList.get(1)))
                .sellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(2))))
                .buyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(incomingRateInList.get(3))))
                .build();

        return repository.saveRateToFile(LocalDate.parse(incomingRateInList.get(0)), newRate);
    }

    @Override
    public boolean removeExistingRate(List<String> inputRate) {

        LocalDate date = LocalDate.parse(inputRate.get(0));
        Map<Currency, String> existingRatesList = repository.getRatesFromFile(date);

        if (existingRatesList.containsKey(Currency.getInstance(inputRate.get(1)))) {
            return repository.removeRateFromFile(date, Currency.getInstance(inputRate.get(1)));
        }
        return false;
    }

    @Override
    public List<String> getListExchangeRates(List<String> inputRate) {

        Map<Currency, String> receivedList = repository.getRatesFromFile(LocalDate.parse(inputRate.get(0)));
        return new ArrayList<>(receivedList.values());
    }

    @Override
    public BigDecimal exchangeCurrencies(List<String> inputRate) {

        Map<Currency, String> ratesFromFile = repository.getRatesFromFile(LocalDate.parse(inputRate.get(0)));
        BigDecimal inputCur = BigDecimal.valueOf(Double.parseDouble(inputRate.get(1)));
        BigDecimal firstExchangeValue = BigDecimal.ZERO;
        BigDecimal secondExchangeValue = BigDecimal.ZERO;

        for (Map.Entry<Currency, String> itm : ratesFromFile.entrySet()) {

            if (itm.getKey().equals(Currency.getInstance(inputRate.get(2)))) {
                String[] tmpArray = itm.getValue().split(",");
                firstExchangeValue = BigDecimal.valueOf(Double.parseDouble(tmpArray[1]));
            }
            if (itm.getKey().equals(Currency.getInstance(inputRate.get(3)))) {
                String[] tmpArray = itm.getValue().split(",");
                secondExchangeValue = BigDecimal.valueOf(Double.parseDouble(tmpArray[2]));
            }

            if (!firstExchangeValue.equals(BigDecimal.ZERO) && !secondExchangeValue.equals(BigDecimal.ZERO)) {
                break;
            }
        }
        return inputCur.multiply(firstExchangeValue).divide(secondExchangeValue, 2, RoundingMode.HALF_UP);
    }
}
