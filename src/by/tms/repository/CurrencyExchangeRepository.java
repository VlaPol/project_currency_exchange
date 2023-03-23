package by.tms.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;

public class CurrencyExchangeRepository {

    public void saveRate(String currentValue) throws IOException {

        String PATH = "data/exchange_rate/";
        File directory = new File(PATH);

        if (! directory.exists()){
            directory.mkdirs();
        }

        Currency instance = Currency.getInstance(currentValue);

        LocalDate currentDate = LocalDate.now();

        File csvFile = new File( PATH + currentDate + ".csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        fileWriter.write(instance + ",2.821,2.889");
        fileWriter.close();
    }
}
