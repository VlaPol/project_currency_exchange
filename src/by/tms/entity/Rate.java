package by.tms.entity;

import java.math.BigDecimal;
import java.util.Currency;

public class Rate {

    private Currency currencyCode;
    private BigDecimal sellCurrencyValue;
    private BigDecimal buyCurrencyValue;

    public Rate(Currency currencyCode, BigDecimal sellCurrencyValue, BigDecimal buyCurrencyValue) {
        this.currencyCode = currencyCode;
        this.sellCurrencyValue = sellCurrencyValue;
        this.buyCurrencyValue = buyCurrencyValue;
    }

    public Rate() {
    }
    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setSellCurrencyValue(BigDecimal sellCurrencyValue) {
        this.sellCurrencyValue = sellCurrencyValue;
    }

    public void setBuyCurrencyValue(BigDecimal buyCurrencyValue) {
        this.buyCurrencyValue = buyCurrencyValue;
    }

    @Override
    public String toString() {
        return currencyCode +
                ", " + sellCurrencyValue +
                ", " + buyCurrencyValue;
    }
}
