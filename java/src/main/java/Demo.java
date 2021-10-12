package demo;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Demo {
    public static boolean isLong(String input) {
        return input.length() > 5;
    }
}

