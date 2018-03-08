package com.logicbig.example;

import org.springframework.boot.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootConfiguration
public class ExampleExitCodeExceptionMapper {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    ApplicationRunner appRunner() {
        return new MyAppRunner();
    }

    @Bean
    ExitCodeExceptionMapper exitCodeExceptionMapper() {
        return exception -> {

            if (exception.getCause() instanceof MyException) {
                return 10;
            }
            return 1;
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleExitCodeExceptionMapper.class, args);
    }

    private static class MyAppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("in command line");
            if (true) throw new MyException("test");
        }
    }

    private static class MyBean {

        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("-- ExitCodeEvent --");
            System.out.println("exit code: " + event.getExitCode());
        }
    }

    private static class MyException extends RuntimeException {

        public MyException(String message) {
            super(message);
        }

    }
}