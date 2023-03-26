package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class ContainsInputCurrencyExcepton extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Данные о курсе валюты отсутствуют";
    }
}
