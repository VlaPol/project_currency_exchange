package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class LocalCurrencyException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Местная валюта";
    }
}
