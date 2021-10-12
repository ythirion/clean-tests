package customer;

import lombok.experimental.UtilityClass;
import lombok.val;

import static java.lang.Math.min;

@UtilityClass
public class PriceEngine {
    public static double calculateDiscount(Product... products) {
        val discount = products.length * 0.01;
        return min(discount, 0.2);
    }
}