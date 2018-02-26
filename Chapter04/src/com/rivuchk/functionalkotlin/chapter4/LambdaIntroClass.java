package com.rivuchk.functionalkotlin.chapter4;

public class LambdaIntroClass {

    interface SomeInterface {
        void doSomeStuff();
    }

    private static void invokeSomeStuff(SomeInterface someInterface) {
        someInterface.doSomeStuff();
    }

    public static void main(String[] args) {
        invokeSomeStuff(()->{
                System.out.println("doSomeStuff called");
        });
    }
}
