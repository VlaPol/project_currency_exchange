import by.tms.config.Property;
import by.tms.controller.CurrencyExchangeController;
import by.tms.repository.CurrencyExchangeRepository;
import by.tms.repository.CurrencyExchangeRepositoryImpl;
import by.tms.service.CurrencyExchangeService;
import by.tms.service.CurrencyExchangeServiceImpl;

import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Path fileDirectory = Path.of(System.getenv("PATH_CURRENCY_REPOSITORY"));

        CurrencyExchangeRepository repository = new CurrencyExchangeRepositoryImpl(new Property(fileDirectory));
        CurrencyExchangeService service = new CurrencyExchangeServiceImpl(repository);
        CurrencyExchangeController controller = new CurrencyExchangeController(service);

        if (args.length > 0) {
            String userCommand = args[0];
            List<String> inputValues = List.of(args).subList(1, args.length);
            controller.inputCommandHandler(userCommand, inputValues);
        } else {
            controller.inputCommandHandler("", List.of());
        }
    }
}