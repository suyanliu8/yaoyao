package com.suyanliu.yaoyao;

@Yaoyao
@Liusy(name = "Me")
public class App {
    private String value;
    private String value2;

    public App(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        App app = new App("it works");
        System.out.println(app.getValue());

        com.suyanliu.yaoyao.MeTest mt = new com.suyanliu.yaoyao.MeTest();
        System.out.println(mt.getMessage());

        com.suyanliu.yaoyao.impl.MeTest2 mt2 = new com.suyanliu.yaoyao.impl.MeTest2();
        System.out.println(mt2.getMessage());

    }
}
