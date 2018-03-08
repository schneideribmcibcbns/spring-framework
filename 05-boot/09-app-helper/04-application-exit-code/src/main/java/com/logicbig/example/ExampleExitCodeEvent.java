package com.logicbig.example;

import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootConfiguration
public class ExampleExitCodeEvent {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    MyBean2 myBean2() {
        return new MyBean2();
    }

    public static void main(String[] args) {
        ApplicationContext context =
                SpringApplication.run(ExampleExitCodeEvent.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        int exit = SpringApplication.exit(context);
        System.exit(exit);
    }

    private static class MyBean implements ExitCodeGenerator {

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @Override
        public int getExitCode() {
            return 100;
        }
    }

    private static class MyBean2 {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }
}