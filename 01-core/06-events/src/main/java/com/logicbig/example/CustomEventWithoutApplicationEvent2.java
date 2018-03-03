package com.logicbig.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

public class CustomEventWithoutApplicationEvent2 {

    @Bean
    AListenerBean listenerBean () {
        return new AListenerBean();
    }

    @Bean
    MyEvenPublisherBean publisherBean () {
        return new MyEvenPublisherBean();
    }


    public static void main (String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                            CustomEventWithoutApplicationEvent2.class);
        MyEvenPublisherBean bean = context.getBean(MyEvenPublisherBean.class);
        bean.sendMsg("A test message");

    }

    //we are not autowiring ApplicationEventPublisher but implementing ApplicationEventPublisherAware this time
    private static class MyEvenPublisherBean implements ApplicationEventPublisherAware {

        ApplicationEventPublisher publisher;

        public void sendMsg (String msg) {
            publisher.publishEvent(new MyEvent(msg));

        }

        @Override
        public void setApplicationEventPublisher (
                            ApplicationEventPublisher applicationEventPublisher) {
            this.publisher = applicationEventPublisher;

        }
    }

    private static class AListenerBean {

        @EventListener
        public void onMyEvent (MyEvent event) {
            System.out.print("event received: " + event.getMsg());

        }
    }

    private static class MyEvent {
        private final String msg;


        public MyEvent (String msg) {
            this.msg = msg;
        }

        public String getMsg () {
            return msg;
        }
    }
}