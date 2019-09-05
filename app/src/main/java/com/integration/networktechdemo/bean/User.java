package com.integration.networktechdemo.bean;

/**
 * Created by Wongerfeng on 2019/8/21.
 */
public class User {

    public String name;
    public int age;
    public long height;
    public float weight;
    public boolean married;

    public User() {
        name = "";
        age = 0;
        height = 0L;
        weight = 0.0f;
        married = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", married=" + married +
                '}';
    }
}
