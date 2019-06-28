package com.coderjks.designpattern.singleton;

// [출처] https://www.baeldung.com/java-singleton
public class SingletonPattern {
    public static void main(String[] args) {
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE.getInstance();
        System.out.println(enumSingleton1.getInfo());

        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE.getInstance();
        enumSingleton2.setInfo("New enum Info");

        System.out.println(enumSingleton1.getInfo());
        System.out.println(enumSingleton2.getInfo());
    }
}
class DoubleCheckedLockingSingleton {
    private static volatile DoubleCheckedLockingSingleton instance;
    private DoubleCheckedLockingSingleton() {}

    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
}
enum EnumSingleton {
    INSTANCE("Initial class info");

    private String info;

    private EnumSingleton(String info) {
        this.info = info;
    }

    public EnumSingleton getInstance() {
        return INSTANCE;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    //getters and setters
}
