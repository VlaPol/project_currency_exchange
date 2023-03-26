package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class IncorrectInputCurrencyForExchangeException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неподдерживаемая исходная валюта";
    }
}
