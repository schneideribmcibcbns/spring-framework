package com.logicbig.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

public class CustomEventWithoutApplicationEvent {

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
                            CustomEventWithoutApplicationEvent.class);
        MyEvenPublisherBean bean = context.getBean(MyEvenPublisherBean.class);
        bean.sendMsg("A test message");

    }

    private static class MyEvenPublisherBean{
        @Autowired
        ApplicationEventPublisher publisher;

        public void sendMsg(String msg){
            publisher.publishEvent(new MyEvent(msg));

        }

    }
    private static class AListenerBean {

        @EventListener
        public void onMyEvent (MyEvent event) {
            System.out.print("event received: "+event.getMsg());

        }
    }

    /**
     * This time we are not extending any ApplicationEvent
     */
    private static class MyEvent{
        private final String msg;


        public MyEvent (String msg) {
            this.msg = msg;
        }

        public String getMsg () {
            return msg;
        }
    }
}