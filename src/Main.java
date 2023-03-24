import by.tms.config.Property;
import by.tms.entity.Rate;
import by.tms.repository.CurrencyExchangeRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Property property = new Property(System.getenv("LOCAL_CURRENCY"));
        CurrencyExchangeRepository currencyExchangeRepository = new CurrencyExchangeRepository();
        List<Rate> currencyList = List.of(
                new Rate(Currency.getInstance(property.getLocalCurrency()), BigDecimal.valueOf(2.821),BigDecimal.valueOf(2.889)),
                new Rate(Currency.getInstance(property.getLocalCurrency()), BigDecimal.valueOf(2.000), BigDecimal.valueOf(2.999))
        );
        currencyExchangeRepository.saveRateToFile(currencyList);
        List<Rate> ratesFromFile = currencyExchangeRepository.getRatesFromFile("2023-03-24");
        for(Rate itm: ratesFromFile){
            System.out.println(itm);
        }
    }
}