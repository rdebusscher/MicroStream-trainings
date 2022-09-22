package be.rubus.microstream.training.performance.utils;

import org.javamoney.moneta.RoundedMoney;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public final class MoneyUtil {

    private MoneyUtil() {
    }

    /**
     * {@link CurrencyUnit} for this demo, US Dollar is used as only currency.
     */
    public static final CurrencyUnit CURRENCY_UNIT = Monetary.getCurrency(Locale.US);

    /**
     * Multiplicant used to calculate retail prices, adds an 11% margin.
     */
    private final static BigDecimal RETAIL_MULTIPLICANT = scale(BigDecimal.valueOf(1.11));


    private static BigDecimal scale(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Converts a double into a {@link MonetaryAmount}
     *
     * @param number the number to convert
     * @return the converted {@link MonetaryAmount}
     */
    public static MonetaryAmount money(double number) {
        return money(new BigDecimal(number));
    }

    /**
     * Converts a {@link BigDecimal} into a {@link MonetaryAmount}
     *
     * @param number the number to convert
     * @return the converted {@link MonetaryAmount}
     */
    public static MonetaryAmount money(BigDecimal number) {
        return RoundedMoney.of(scale(number), CURRENCY_UNIT);
    }

    /**
     * Calculates the retail price based on a purchase price by adding a margin.
     *
     * @param purchasePrice the purchase price
     * @return the calculated retail price
     * @see #RETAIL_MULTIPLICANT
     */
    public static MonetaryAmount retailPrice(MonetaryAmount purchasePrice) {
        return money(RETAIL_MULTIPLICANT.multiply(BigDecimal.valueOf(purchasePrice.getNumber().doubleValue())));
    }

}
