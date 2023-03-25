package by.tms.controller;

import by.tms.service.CurrencyExchangeService;

import java.util.List;

public class CurrencyExchangeController {

    CurrencyExchangeService service;

    public CurrencyExchangeController(CurrencyExchangeService service) {
        this.service = service;
    }


    public void inputCommandHandler(String command, List<String> inputRate) {

        switch (command) {
            case "admin/putExchangeRate" -> {
                service.addNewExchangeRate(inputRate);
            }
            case "admin/removeExchangeRate" -> {
                service.removeExistingRate(inputRate);
            }
        }
    }
}
