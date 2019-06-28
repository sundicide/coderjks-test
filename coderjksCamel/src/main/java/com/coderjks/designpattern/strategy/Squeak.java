package com.coderjks.designpattern.strategy;

public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("빽뺵");
    }
}
