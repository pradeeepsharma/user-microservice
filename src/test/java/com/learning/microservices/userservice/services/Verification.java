package com.learning.microservices.userservice.services;

import java.util.Arrays;
import java.util.List;

public class Verification {
    public static void main(String[] args) {
        List<Integer> numbers= Arrays.asList(2,4,7,4,8,11,19,16);
        Integer integer = numbers.stream().filter(element -> element % 2 != 0).map(e -> e * e).reduce((e1,e2)->e1+e2).get();
        System.out.println("Sum of sqaure of odd numbers :"+integer);
    }
}
