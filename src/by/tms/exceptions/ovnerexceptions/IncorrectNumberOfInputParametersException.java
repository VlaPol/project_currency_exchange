package by.tms.exceptions.ovnerexceptions;

import by.tms.exceptions.AppException;

public class IncorrectNumberOfInputParametersException extends AppException {
    @Override
    public String getExceptionMessage() {
        return "Неверный формат команды";
    }
}
