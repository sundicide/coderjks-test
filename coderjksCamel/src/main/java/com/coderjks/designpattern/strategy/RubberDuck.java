package com.coderjks.designpattern.strategy;

public class RubberDuck extends Duck {

    public RubberDuck() {
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("고무오리");
    }
}
