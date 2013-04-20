package com.tw.vrc.problems;

public class Exponentiation {
    private Double value;

    public Exponentiation(Double value) {
        this.value = value;
    }

    public Double to(Integer power) {
        return realPower(value, power);
    }

    private Double realPower(Double value, Integer power) {
        if(power == 1) return value;
        if(power <= 0) return 1d;
        Double x = realPower(value, power / 2);
        if (power % 2 == 0) return x * x;
        else return value * x * x;
    }
}
