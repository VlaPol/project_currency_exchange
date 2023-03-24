package by.tms.repository;

import by.tms.entity.Rate;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CurrencyExchangeRepository {
    private final static String PATH = "data/exchange_rate/";

    public void saveRateToFile(List<Rate> currencyList) throws IOException {

        File directory = new File(PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        LocalDate currentDate = LocalDate.now();

        File csvFile = new File(PATH + currentDate + ".csv");
        Writer fileWriter = new FileWriter(csvFile);
        for (Rate itm: currencyList){
            fileWriter.write(String.valueOf(itm));
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
    }

    public List<Rate> getRatesFromFile(String currencyRatesDate) throws IOException {

        String line;
        List<Rate> currencyList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(PATH + currencyRatesDate + ".csv"));

        while ((line = br.readLine()) != null) {
            String[] stringParts = line.split(",");
            Rate rate = new Rate();
            rate.setCurrencyCode(Currency.getInstance(stringParts[0]));
            rate.setSellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[1])));
            rate.setBuyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[2])));
            currencyList.add(rate);
        }

        return currencyList;
    }
}
