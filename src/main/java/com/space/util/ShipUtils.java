package com.space.util;

import com.space.model.Ship;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShipUtils {

    private ShipUtils() {}

    public static double countRating(Ship ship) {
        double speed = ship.getSpeed();
        int year = ship.getProdDate().getYear();

        double n1 = 80 * speed * (ship.isUsed() ? 0.5 : 1);
        double n2 = 3019 - year + 1;

        BigDecimal bd = new BigDecimal(Double.toString(n1/n2));
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
