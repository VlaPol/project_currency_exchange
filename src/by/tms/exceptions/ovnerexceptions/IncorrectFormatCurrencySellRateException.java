package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class IncorrectFormatCurrencySellRateException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверный формат курса продажи";
    }
}
