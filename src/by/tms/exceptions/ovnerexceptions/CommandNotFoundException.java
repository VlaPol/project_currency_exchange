package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class CommandNotFoundException extends AppException {

    @Override
    public String getExceptionMessage() {
        return "Введенная команда не существует";
    }
}
