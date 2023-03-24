import by.tms.config.Property;
import by.tms.repository.CurrencyExchangeRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Property property = new Property(System.getenv("LOCAL_CURRENCY"));
        CurrencyExchangeRepository currencyExchangeRepository = new CurrencyExchangeRepository();
        currencyExchangeRepository.saveRateToFile(property.getLocalCurrency());
        currencyExchangeRepository.getRateFromFile("2023-03-23");
    }
}