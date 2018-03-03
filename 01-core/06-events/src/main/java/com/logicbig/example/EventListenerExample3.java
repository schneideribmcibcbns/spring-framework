package com.logicbig.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;


@Configuration
public class EventListenerExample3 {
    @Bean
    AListenerBean listenerBean () {
        return new AListenerBean();
    }

    @Bean
    AnotherListenerBean anotherListenerBean () {
        return new AnotherListenerBean();
    }


    public static void main (String[] args) {

        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                EventListenerExample3.class);
        context.publishEvent(new MyEvent("test message 1"));
    }

    private static class AListenerBean {

        @EventListener
        public MyAnotherEvent handleContextEvent (MyEvent myEvent) {
            System.out.println("event received: " + myEvent);
            return new MyAnotherEvent(LocalDateTime.now());
        }
    }

    private static class AnotherListenerBean {

        @EventListener
        public void handleContextEvent (MyAnotherEvent myEvent) {
            System.out.println("event received: " + myEvent);
        }
    }

    private static class MyEvent {
        private String msg;

        public MyEvent (String msg) {
            this.msg = msg;
        }

        public void setMsg (String msg) {
            this.msg = msg;
        }

        public String getMsg () {
            return msg;
        }

        @Override
        public String toString () {
            return "MyEvent{" +
                                "msg='" + msg + '\'' +
                                '}';
        }
    }

    private static class MyAnotherEvent {
        private LocalDateTime time;

        public MyAnotherEvent (LocalDateTime time) {
            this.time = time;
        }

        public LocalDateTime getTime () {
            return time;
        }

        public void setTime (LocalDateTime time) {
            this.time = time;
        }

        @Override
        public String toString () {
            return "MyAnotherEvent{" +
                                "time=" + time +
                                '}';
        }
    }
}