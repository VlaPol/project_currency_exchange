package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class IncorrectValueCurrencyBuyRateException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверное значение курса покупка";
    }
}
