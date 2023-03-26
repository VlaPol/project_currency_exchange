package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class IncorrectOutputCurrencyForExchangeException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неподдерживаемая целевая валюта";
    }
}
