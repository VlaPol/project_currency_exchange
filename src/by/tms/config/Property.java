package by.tms.config;

public class Property {

    private final String LOCAL_CURRENCY_CODE;

    public Property(String lcc) {
        this.LOCAL_CURRENCY_CODE = lcc;
    }

    public String getLocalCurrency(){
        return LOCAL_CURRENCY_CODE;
    }
}
