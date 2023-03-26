package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class DeleteLocalCurrencyException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Базовая валюта";
    }
}
