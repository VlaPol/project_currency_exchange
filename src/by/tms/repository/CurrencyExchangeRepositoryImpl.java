package by.tms.repository;

import by.tms.config.Property;
import by.tms.entity.Rate;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CurrencyExchangeRepositoryImpl implements CurrencyExchangeRepository {

    private final Property property;

    public CurrencyExchangeRepositoryImpl(Property property) {
        this.property = property;

        File fileDirectory = new File(property.getFileDirectory().toUri());
        if (!(fileDirectory.exists())) {
            fileDirectory.mkdirs();
        }
    }

    @Override
    public void saveRateToFile(Rate currentCurrencyRate) {

        LocalDate currentDate = LocalDate.now();

        try {
            File csvFile = new File(property.getFileDirectory() + "/" + currentDate + ".csv");
            Writer fileWriter = new FileWriter(csvFile, true);
            fileWriter.write(String.valueOf(currentCurrencyRate));
            fileWriter.write(System.lineSeparator());

            fileWriter.close();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);

        }
    }

    @Override
    public List<Rate> getRatesFromFile(String currencyRatesDate) {

        String line;
        List<Rate> currencyList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(property.getFileDirectory() + "/" + currencyRatesDate + ".csv")
            );

            while ((line = br.readLine()) != null) {
                String[] stringParts = line.split(",");
                Rate rate = new Rate();
                rate.setCurrencyCode(Currency.getInstance(stringParts[0]));
                rate.setSellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[1])));
                rate.setBuyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[2])));
                currencyList.add(rate);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }

        return currencyList;
    }
}
