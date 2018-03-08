package com.logicbig.example;

import org.springframework.boot.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootConfiguration
public class ExampleExitCodeGeneratorException {
    @Bean
    ApplicationRunner appRunner() {
        return new MyAppRunner();
    }

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleExitCodeGeneratorException.class, args);
    }

    private static class MyAppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("in ApplicationRunner#run method");
            throw new MyExitCodeException("test exception");
        }
    }

    private static class MyExitCodeException extends RuntimeException
            implements ExitCodeGenerator {

        public MyExitCodeException(String message) {
            super(message);
        }

        @Override
        public int getExitCode() {
            return 5;
        }
    }

    private static class MyBean {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }
}