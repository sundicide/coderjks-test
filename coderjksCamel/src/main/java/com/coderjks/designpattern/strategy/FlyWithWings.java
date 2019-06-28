package com.coderjks.designpattern.strategy;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("날다");
    }
}
