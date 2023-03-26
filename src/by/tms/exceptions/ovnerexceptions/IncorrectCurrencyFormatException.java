package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class IncorrectCurrencyFormatException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неподдерживаемая валюта";
    }
}
