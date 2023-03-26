package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class IncorrectInputCurrencyException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверный формат исходной денежной суммы";
    }
}
