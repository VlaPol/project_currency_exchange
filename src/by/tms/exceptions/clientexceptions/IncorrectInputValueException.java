package by.tms.exceptions.clientexceptions;

import by.tms.exceptions.AppException;

public class IncorrectInputValueException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверное значение исходной денежной суммы";
    }
}
