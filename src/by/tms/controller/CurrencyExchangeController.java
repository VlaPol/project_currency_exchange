package by.tms.controller;

import by.tms.exceptions.AppException;
import by.tms.exceptions.clientexceptions.*;
import by.tms.exceptions.ovnerexceptions.*;
import by.tms.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CurrencyExchangeController {

    CurrencyExchangeService service;
    public static final int OWNER_PUT_EXCHANGE_RATE_SIZE = 4;
    public static final int OWNER_REMOVE_EXCHANGE_RATE_SIZE = 2;
    public static final int OWNER_LIST_EXCHANGE_RATE_SIZE = 1;
    public static final int CLIENT_PUT_CURRENCY_TO_EXCHANGE_SIZE = 4;

    public CurrencyExchangeController(CurrencyExchangeService service) {
        this.service = service;
    }

    public void inputCommandHandler(String command, List<String> inputRate) {

        try {

            if (!isInputCommandRight(command, inputRate)) {
                throw new IncorrectNumberOfInputParametersException();
            }
            if (!isInputDateCorrect(inputRate.get(0))) {
                throw new IncorrectDateFormatException();
            }

            switch (command) {
                case "admin/putExchangeRate" -> {
                    if (isInputCurrencyCorrect(inputRate.get(1))
                            && !isInputCurrencyLocal("put", inputRate.get(1))
                            && isInputDataIsCurrency("buy", inputRate.get(2))
                            && isInputDataIsCurrency("sell", inputRate.get(3))
                            && isInputDataMoreThanZero("buy", inputRate.get(2))
                            && isInputDataMoreThanZero("sell", inputRate.get(3))) {
                        if (service.addNewExchangeRate(inputRate)) {
                            System.out.println("Запись сохранена");
                        }
                    }
                }
                case "admin/removeExchangeRate" -> {
                    if (isInputCurrencyCorrect(inputRate.get(1))
                            && !isInputCurrencyLocal("remove", inputRate.get(1))) {
                        if (service.removeExistingRate(inputRate)) {
                            System.out.println("Запись удалена");
                        } else {
                            System.err.println("Записи не существует");
                        }
                    }
                }
                case "listExchangeRates" -> {
                    List<String> outputList = service.getListExchangeRates(inputRate);
                    if (!outputList.isEmpty()) {
                        System.out.format("%s %10s %10s", "Валюта", "Покупка", "Продажа");
                        System.out.println();
                        for (String itm : outputList) {
                            String[] tmpArray = itm.split(",");
                            System.out.printf("%-9s %-10s %s", tmpArray[0], tmpArray[1], tmpArray[2]);
                            System.out.println();
                        }
                    } else {
                        System.out.println("Данные отсутствуют");
                    }
                }
                case "exchange" -> {
                    if (isInputCurrencyIsCorrect(inputRate)
                            && isOutputCurrencyIsCorrect(inputRate)
                            && isCorrectCurrencyValueInput(inputRate.get(1))
                            && !isInputCurrencyNegative(inputRate.get(1))
                            && areInputCurrenciesInList(inputRate)) {
                        System.out.println("К выдаче: " + service.exchangeCurrencies(inputRate));
                    }
                }
                default -> throw new CommandNotFoundException();
            }
        } catch (AppException exception) {
            System.err.println(exception.getExceptionMessage());

        } catch (Exception e) {
            System.err.println("Неизвестная ошибка");
        }
    }

    /**
     * Проверяем на корректность коды вносимой валюты
     *
     * @param inputRate
     * @return true
     * @throw IncorrectInputCurrencyForExchangeException()
     */
    private boolean isInputCurrencyIsCorrect(List<String> inputRate) {
        try {
            Currency.getInstance(inputRate.get(2));
            return true;
        } catch (Exception exception) {
            throw new IncorrectInputCurrencyForExchangeException();
        }

    }

    /**
     * Проверяем на корректность коды желаемой валюты
     *
     * @param inputRate
     * @return true
     * @throw IncorrectOutputCurrencyForExchangeException()
     */
    private boolean isOutputCurrencyIsCorrect(List<String> inputRate) {
        try {
            Currency.getInstance(inputRate.get(3));
            return true;
        } catch (Exception exception) {
            throw new IncorrectOutputCurrencyForExchangeException();
        }

    }

    /**
     * Проверяем сумму на отрицательное значние
     *
     * @param inputCurrency
     * @return false
     * @throw IncorrectInputValueException()
     */
    private boolean isInputCurrencyNegative(String inputCurrency) {
        if (!(BigDecimal.valueOf(Double.parseDouble(inputCurrency)).compareTo(BigDecimal.ZERO) < 0)) {
            return false;
        } else {
            throw new IncorrectInputValueException();
        }
    }

    /**
     * Проверяем введенную сумму на правильность
     *
     * @param inputCurrency
     * @return true
     * @throw IncorrectInputCurrencyException()
     */
    private boolean isCorrectCurrencyValueInput(String inputCurrency) {

        try {
            BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(inputCurrency));
            return true;
        } catch (NumberFormatException exception) {
            throw new IncorrectInputCurrencyException();
        }
    }

    /**
     * Проверяем есть ли обе валюты в списке курсов валют
     *
     * @param inputRate
     * @return true
     * @throw ContainsInputCurrencyExcepton()
     */
    private boolean areInputCurrenciesInList(List<String> inputRate) {

        List<String> listExchangeRates = service.getListExchangeRates(inputRate);
        boolean hasFirstCurrency = false;
        boolean hasSecondCurrency = false;
        for (String itm : listExchangeRates) {
            if (itm.contains(inputRate.get(2))) {
                hasFirstCurrency = true;
                continue;
            }
            if (itm.contains(inputRate.get(3))) {
                hasSecondCurrency = true;
                continue;
            }
        }
        if (hasFirstCurrency && hasSecondCurrency) {
            return true;
        } else {
            throw new ContainsInputCurrencyException();
        }
    }

    /**
     * Проверка даты
     *
     * @param inputDate
     * @return true
     * @throw IncorrectDateFormatException()
     */
    private boolean isInputDateCorrect(String inputDate) {
        try {
            LocalDate.parse(inputDate);
            return true;
        } catch (Exception exception) {
            throw new IncorrectDateFormatException();
        }
    }

    /**
     * Проверка введенной строки на количество парамеров
     *
     * @param command
     * @param inputRate
     * @return true
     */
    private boolean isInputCommandRight(String command, List<String> inputRate) {

        switch (command) {
            case "admin/putExchangeRate" -> {
                if (inputRate.size() == OWNER_PUT_EXCHANGE_RATE_SIZE) return true;
            }
            case "admin/removeExchangeRate" -> {
                if (inputRate.size() == OWNER_REMOVE_EXCHANGE_RATE_SIZE) return true;
            }
            case "listExchangeRates" -> {
                if (inputRate.size() == OWNER_LIST_EXCHANGE_RATE_SIZE) return true;
            }
            case "exchange" -> {
                if (inputRate.size() == CLIENT_PUT_CURRENCY_TO_EXCHANGE_SIZE) return true;
            }
        }

        return false;
    }

    /**
     * Проверка введенной валюты
     *
     * @param inputCurrency
     * @return true
     * @throw IncorrectCurrencyFormatException()
     */
    private boolean isInputCurrencyCorrect(String inputCurrency) {

        try {
            Currency.getInstance(inputCurrency);
            return true;
        } catch (Exception exception) {
            throw new IncorrectCurrencyFormatException();
        }
    }

    /**
     * Проверка локальной валюты. Если action == "put" - проверяем базовую валюту при добавлении,
     * иначе - при удалении
     *
     * @param inputCurrency
     * @return false
     * @throw LocalCurrencyException() or DeleteLocalCurrencyException()
     */
    private boolean isInputCurrencyLocal(String action, String inputCurrency) {

        Currency localeCurrency = Currency.getInstance(Locale.getDefault());
        if (!Currency.getInstance(inputCurrency).equals(localeCurrency))
            return false;
        else {
            if (action.equals("put")) {
                throw new LocalCurrencyException();
            } else {
                throw new DeleteLocalCurrencyException();
            }
        }
    }

    /**
     * Проверка на то, введены ли курсы. Если type == "buy" - проверяем курс покупки,
     * иначе - курс продажи
     *
     * @param type
     * @param inputCurrency
     * @return true
     * @throw IncorrectFormatCurrencyBuyRateException() or IncorrectFormatCurrencySellRateException()
     */
    private boolean isInputDataIsCurrency(String type, String inputCurrency) {

        if (type.equals("buy")) {
            try {
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(inputCurrency));
                return true;
            } catch (NumberFormatException exception) {
                throw new IncorrectFormatCurrencyBuyRateException();
            }
        } else {
            try {
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(inputCurrency));
                return true;
            } catch (NumberFormatException exception) {
                throw new IncorrectFormatCurrencySellRateException();
            }
        }
    }

    /**
     * Проверка введенных курсов на значение больше нуля. Если type == "buy" - проверяем курс покупки,
     * иначе - курс продажи
     *
     * @param type
     * @param inputCurrency
     * @return true
     * @throw IncorrectValueCurrencyBuyRateException() or IncorrectValueCurrencySellRateException();
     */
    private boolean isInputDataMoreThanZero(String type, String inputCurrency) {

        if (!(BigDecimal.valueOf(Double.parseDouble(inputCurrency)).compareTo(BigDecimal.ZERO) > 0)) {
            if (type.equals("buy")) throw new IncorrectValueCurrencyBuyRateException();
            if (type.equals("sell")) throw new IncorrectValueCurrencySellRateException();
        }
        return true;
    }

}
