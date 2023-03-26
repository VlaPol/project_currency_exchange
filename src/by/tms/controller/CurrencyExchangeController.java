package by.tms.controller;

import by.tms.exceptions.AppException;
import by.tms.exceptions.ovnerexceptions.*;
import by.tms.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CurrencyExchangeController {

    CurrencyExchangeService service;
    public static final int PUT_EXCHANGE_RATE_SIZE = 4;
    public static final int REMOVE_EXCHANGE_RATE_SIZE = 2;
    public static final int LIST_EXCHANGE_RATE_SIZE = 1;

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
                    if(!outputList.isEmpty()) {
                        System.out.format("%s %10s %10s", "Валюта", "Покупка", "Продажа");
                        System.out.println();
                        for (String itm : outputList) {
                            String[] tmpArray = itm.split(",");
                            System.out.printf("%-9s %-10s %s", tmpArray[0], tmpArray[1], tmpArray[2]);
                            System.out.println();
                            //System.out.println(itm.replace(",", " "));
                        }
                    }else{
                        System.out.println("Данные отсутствуют");
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
     * Проверка даты
     *
     * @param inputDate
     * @return true
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
                if (inputRate.size() == PUT_EXCHANGE_RATE_SIZE) return true;
            }
            case "admin/removeExchangeRate" -> {
                if (inputRate.size() == REMOVE_EXCHANGE_RATE_SIZE) return true;
            }
            case "listExchangeRates" -> {
                if (inputRate.size() == LIST_EXCHANGE_RATE_SIZE) return true;
            }
        }

        return false;
    }

    /**
     * Проверка введенной валюты
     *
     * @param inputCurrency
     * @return true
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
     * Проверка введенных курсы на значение больше нуля. Если type == "buy" - проверяем курс покупки,
     * иначе - курс продажи
     *
     * @param type
     * @param inputCurrency
     * @return true
     */
    private boolean isInputDataMoreThanZero(String type, String inputCurrency) {

        if (!(BigDecimal.valueOf(Double.parseDouble(inputCurrency)).compareTo(BigDecimal.ZERO) > 0)) {
            if (type.equals("buy")) throw new IncorrectValueCurrencyBuyRateException();
            if (type.equals("sell")) throw new IncorrectValueCurrencySellRateException();
        }
        return true;
    }

}
