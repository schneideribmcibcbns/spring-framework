package com.logicbig.example;

import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.Arrays;


public class CmdSourceExample1 {

    public static void main(String[] args) {
        SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
        Arrays.stream(ps.getPropertyNames()).forEach(s ->
                System.out.printf("%s => %s%n", s, ps.getProperty(s)));
    }
}