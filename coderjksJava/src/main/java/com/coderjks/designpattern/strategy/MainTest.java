package com.coderjks.designpattern.strategy;

public class MainTest {
    public static void main(String[] args) {
        Duck rubberDuck = new RubberDuck();
        rubberDuck.performQuack();

        System.out.println("======================");

        rubberDuck.setFlyBehavior(new FlyWithWings());
        rubberDuck.setQuackBehavior(new Quack());

        rubberDuck.performFly();
        rubberDuck.performQuack();
    }
}
