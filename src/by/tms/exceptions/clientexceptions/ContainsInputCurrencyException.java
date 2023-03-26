package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class ContainsInputCurrencyException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Данные о курсе валюты отсутствуют";
    }
}
