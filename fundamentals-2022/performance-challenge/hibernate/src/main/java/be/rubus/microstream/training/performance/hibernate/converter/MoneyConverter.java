package be.rubus.microstream.training.performance.hibernate.converter;



import be.rubus.microstream.training.performance.utils.MoneyUtil;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MoneyConverter implements AttributeConverter<MonetaryAmount, Double> {

    @Override
    public Double convertToDatabaseColumn(
            MonetaryAmount money
    ) {
        return money == null
                ? null
                : money.getNumber().doubleValue()
                ;
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(Double amount) {
        return amount == null
                ? null
                : MoneyUtil.money(amount)
                ;
    }

}
