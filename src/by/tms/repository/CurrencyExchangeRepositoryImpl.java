package by.tms.repository;

import by.tms.config.Property;
import by.tms.entity.Rate;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
    public boolean saveRateToFile(LocalDate currentDate, Rate inputRate) {

        Map<Currency, String> ratesFromFile = getRatesFromFile(currentDate);
        String fileName = property.getFileDirectory() + "/" + currentDate + ".csv";
        try {
            File csvFile = new File(fileName);
            Writer fileWriter;
            if (!ratesFromFile.isEmpty() && ratesFromFile.containsKey(inputRate.getCurrencyCode())) {
                ratesFromFile.put(inputRate.getCurrencyCode(), String.valueOf(inputRate).replace(" ", ","));
                saveRatesMapToFile(ratesFromFile, currentDate);
            } else {
                fileWriter = new FileWriter(csvFile, true);
                fileWriter.write(String.valueOf(inputRate).replace(" ", ","));
                fileWriter.write(System.lineSeparator());
                fileWriter.close();
            }
            return true;

        } catch (IOException exception) {
            throw new UncheckedIOException(exception);

        }
    }

    @Override
    public Map<Currency, String> getRatesFromFile(LocalDate currentDate) {

        String line;
        Map<Currency, String> currencyList = new HashMap<>();
        String fileName = property.getFileDirectory() + "/" + currentDate + ".csv";
        try {
            if (!new File(fileName).exists()) {
                return currencyList;
            }

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                String[] stringParts = line.split(",");
                String newRate = Currency.getInstance(stringParts[0]) + "," +
                        BigDecimal.valueOf(Double.parseDouble(stringParts[1])) + "," +
                        BigDecimal.valueOf(Double.parseDouble(stringParts[2]));
                currencyList.put(Currency.getInstance(stringParts[0]), newRate);
            }
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }

        return currencyList;
    }

    @Override
    public boolean removeRateFromFile(LocalDate date, Currency deletedCurrency) {

        Map<Currency, String> currencyList = getRatesFromFile(date);
        currencyList.remove(deletedCurrency);
        try {
            saveRatesMapToFile(currencyList, date);
            return true;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }

    }

    private void saveRatesMapToFile(Map<Currency, String> ratesFromFile, LocalDate currentDate) throws IOException {
        String fileName = property.getFileDirectory() + "/" + currentDate + ".csv";
        File csvFile = new File(fileName);
        Writer fileWriter = new FileWriter(csvFile);
        for (Map.Entry<Currency, String> itm : ratesFromFile.entrySet()) {
            fileWriter.write(String.valueOf(itm.getValue()));
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
    }

}
