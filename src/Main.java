import by.tms.config.Property;
import by.tms.controller.CurrencyExchangeController;
import by.tms.entity.Rate;
import by.tms.repository.CurrencyExchangeRepository;
import by.tms.repository.CurrencyExchangeRepositoryImpl;
import by.tms.service.CurrencyExchangeService;
import by.tms.service.CurrencyExchangeServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Currency;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Path fileDirectory = Path.of(System.getenv("PATH_CURRENCY_REPOSITORY"));
        Property propertie = new Property(fileDirectory);

        CurrencyExchangeRepository repository = new CurrencyExchangeRepositoryImpl(propertie);
        repository.saveRateToFile(new Rate(Currency.getInstance("USD"), BigDecimal.valueOf(2.821),BigDecimal.valueOf(3.889)));
        System.out.println(repository.getRatesFromFile("2023-03-25"));
        CurrencyExchangeService service = new CurrencyExchangeServiceImpl(repository);
        CurrencyExchangeController controller = new CurrencyExchangeController(service);

        if (args.length > 0) {
            String userCommand = args[0];
            List<String> options = List.of(args).subList(1, args.length);
            controller.inputCommandHandler(userCommand, options);
        } else {
            controller.inputCommandHandler("", List.of());
        }

    }
}