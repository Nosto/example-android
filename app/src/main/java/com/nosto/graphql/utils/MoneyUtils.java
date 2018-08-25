package com.nosto.graphql.utils;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class MoneyUtils {

    public static String getFormattedCurrencyString(String isoCurrencyCode, double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(isoCurrencyCode);

        String symbol;
        if (isoCurrencyCode.equalsIgnoreCase("usd") && !Locale.getDefault().equals(Locale.US)) {
            symbol = "US$";
        } else {
            symbol = currency.getSymbol(Locale.US); // US locale has the best symbol formatting table.
        }

        DecimalFormatSymbols decimalFormatSymbols = ((java.text.DecimalFormat) currencyFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol(symbol);
        ((java.text.DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);
        return currencyFormat.format(amount);
    }
}
