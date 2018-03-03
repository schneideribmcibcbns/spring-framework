package com.logicbig.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

public class CustomEventWithApplicationEvent {

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
                            CustomEventWithApplicationEvent.class);
        MyEvenPublisherBean bean = context.getBean(MyEvenPublisherBean.class);
        bean.sendMsg("A test message");

    }
    
    private static class MyEvenPublisherBean{
        @Autowired
        ApplicationEventPublisher publisher;

        public void sendMsg(String msg){
            publisher.publishEvent(new MyEvent(this, msg));

        }

    }
    private static class AListenerBean {

        @EventListener
        public void onMyEvent (MyEvent event) {
            System.out.print("event received: "+event.getMsg());
            System.out.println(" -- source: "+event.getSource());
        }
    }

    private static class MyEvent extends ApplicationEvent{
        private final String msg;


        public MyEvent (Object source, String msg) {
            super(source);
            this.msg = msg;
        }

        public String getMsg () {
            return msg;
        }
    }
}