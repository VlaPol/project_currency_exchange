import by.tms.config.Property;
import by.tms.controller.CurrencyExchangeController;
import by.tms.repository.CurrencyExchangeRepository;
import by.tms.repository.CurrencyExchangeRepositoryImpl;
import by.tms.service.CurrencyExchangeService;
import by.tms.service.CurrencyExchangeServiceImpl;

import java.nio.file.Path;
import java.util.Currency;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Path fileDirectory = Path.of(System.getenv("PATH_CURRENCY_REPOSITORY"));
        Currency currentCurrencyCode = Currency.getInstance(System.getenv("LOCAL_CURRENCY_CODE"));
        Property property = new Property(fileDirectory, currentCurrencyCode);

        CurrencyExchangeRepository repository = new CurrencyExchangeRepositoryImpl(property);
        CurrencyExchangeService service = new CurrencyExchangeServiceImpl(repository);
        CurrencyExchangeController controller = new CurrencyExchangeController(service, property);

        if (args.length > 0) {
            String userCommand = args[0];
            List<String> inputValues = List.of(args).subList(1, args.length);
            controller.inputCommandHandler(userCommand, inputValues);
        } else {
            controller.inputCommandHandler("", List.of());
        }
    }
}