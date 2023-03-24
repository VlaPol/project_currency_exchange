package by.tms.repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CurrencyExchangeRepository {
    private final static String PATH = "data/exchange_rate/";

    public void saveRateToFile(String currentValue) throws IOException {

        File directory = new File(PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        Currency instance = Currency.getInstance(currentValue);

        LocalDate currentDate = LocalDate.now();

        File csvFile = new File(PATH + currentDate + ".csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        fileWriter.write(instance + ",2.821,2.889");
        fileWriter.close();
    }

    public void getRateFromFile(String fileName) throws IOException {

        List<String> currencyList = new ArrayList<>();

        BufferedReader br;
        String line;

        br = new BufferedReader(new FileReader(PATH + fileName + ".csv"));
        while ((line = br.readLine()) != null) {
            currencyList.add(line);
        }

        for(String itm: currencyList){
            System.out.println("***** " + itm + " ******");
        }
    }
}
