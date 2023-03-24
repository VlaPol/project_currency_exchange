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

    public void saveRateToFile(String currentValue) throws IOException {

        Currency instance = Currency.getInstance(currentValue);

        List<Rate> currencyList = List.of(
                new Rate(instance, BigDecimal.valueOf(2.821),BigDecimal.valueOf(2.889)),
                new Rate(instance, BigDecimal.valueOf(2.000), BigDecimal.valueOf(2.999))
        );

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

    public void getRateFromFile(String fileName) throws IOException {

        List<Rate> currencyList = new ArrayList<>();

        BufferedReader br;
        String line;

        br = new BufferedReader(new FileReader(PATH + fileName + ".csv"));
        while ((line = br.readLine()) != null) {
            String[] stringParts = line.split(",");
            Rate rate = new Rate();
            rate.setCurrencyCode(Currency.getInstance(stringParts[0]));
            rate.setSellCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[1])));
            rate.setBuyCurrencyValue(BigDecimal.valueOf(Double.parseDouble(stringParts[2])));
            currencyList.add(rate);
        }

        for(Rate itm: currencyList){
            System.out.println("***** " + itm + " ******");
        }
    }
}
