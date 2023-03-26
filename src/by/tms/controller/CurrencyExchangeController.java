package by.tms.controller;

import by.tms.entity.Rate;
import by.tms.exceptions.AppException;
import by.tms.exceptions.ovnerexceptions.CommandNotFoundException;
import by.tms.exceptions.ovnerexceptions.IncorrectNumberOfInputParametersException;
import by.tms.service.CurrencyExchangeService;

import java.util.List;

public class CurrencyExchangeController {

    CurrencyExchangeService service;
    public static final int PUT_EXCHANGE_RATE_SIZE = 4;
    public static final int REMOVE_EXCHANGE_RATE_SIZE = 2;
    public static final int LIST_EXCHANGE_RATE_SIZE = 1;

    public CurrencyExchangeController(CurrencyExchangeService service) {
        this.service = service;
    }

    public void inputCommandHandler(String command, List<String> inputRate) {

       try {

           if(!isInputCommandRight(command, inputRate)){
               throw new IncorrectNumberOfInputParametersException();
           }

           switch (command) {
               case "admin/putExchangeRate" -> {
                   if(service.addNewExchangeRate(inputRate)){
                       System.out.println("Запись сохранена");
                   }
               }
               case "admin/removeExchangeRate" -> {
                   if(service.removeExistingRate(inputRate)){
                       System.out.println("Запись удалена");
                   }else{
                       System.err.println("Записи не существует");
                   }
               }
               case "listExchangeRates" -> {
                   List<Rate> outputList = service.getListExchangeRates(inputRate);
                   System.out.println("Валюта Покупка Продажа");
                   for (Rate itm : outputList) {
                       System.out.println(itm);
                   }
               }
               default -> throw new CommandNotFoundException();
           }
       } catch (AppException exception) {
           System.err.println(exception.getExceptionMessage());

       } catch (Exception e) {
           System.err.println("Неизвестная ошибка");
       }
    }

    private boolean isInputCommandRight(String command, List<String> inputRate){

        switch (command) {
            case "admin/putExchangeRate" -> {
                if(inputRate.size() == PUT_EXCHANGE_RATE_SIZE) return true;
            }
            case "admin/removeExchangeRate" -> {
                if(inputRate.size() == REMOVE_EXCHANGE_RATE_SIZE) return true;
            }
            case "listExchangeRates" -> {
                if(inputRate.size() == LIST_EXCHANGE_RATE_SIZE) return true;
            }
        }

        return false;
    }

}
