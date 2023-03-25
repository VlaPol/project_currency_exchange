package by.tms.entity;

import java.math.BigDecimal;
import java.util.Currency;

public class Rate {

    private final Currency currencyCode;
    private final BigDecimal sellCurrencyValue;
    private final BigDecimal buyCurrencyValue;

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return currencyCode +
                " " + sellCurrencyValue +
                " " + buyCurrencyValue;
    }

    public static class Builder {
        private Currency currencyCode;
        private BigDecimal sellCurrencyValue;
        private BigDecimal buyCurrencyValue;

        public Builder currencyCode(Currency currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder sellCurrencyValue(BigDecimal sellCurrencyValue) {
            this.sellCurrencyValue = sellCurrencyValue;
            return this;
        }

        public Builder buyCurrencyValue(BigDecimal buyCurrencyValue) {
            this.buyCurrencyValue = buyCurrencyValue;
            return this;
        }

        public Rate build() {
            return new Rate(this);
        }
    }

    private Rate(Builder builder) {
        currencyCode = builder.currencyCode;
        sellCurrencyValue = builder.sellCurrencyValue;
        buyCurrencyValue = builder.buyCurrencyValue;
    }
}
