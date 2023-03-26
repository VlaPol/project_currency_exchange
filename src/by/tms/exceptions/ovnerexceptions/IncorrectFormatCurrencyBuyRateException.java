package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class IncorrectFormatCurrencyBuyRateException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверный формат курса покупки";
    }
}
