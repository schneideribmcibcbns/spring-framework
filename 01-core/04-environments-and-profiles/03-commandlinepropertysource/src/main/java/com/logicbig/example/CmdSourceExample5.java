package com.logicbig.example;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.env.PropertySource;

@Configuration
public class CmdSourceExample5 {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("myProp").withRequiredArg();
        OptionSet options = parser.parse(args);

        PropertySource ps = new JOptCommandLinePropertySource(options);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.getEnvironment().getPropertySources().addFirst(ps);

        context.register(CmdSourceExample5.class);
        context.refresh();

        MyBean bean = context.getBean(MyBean.class);
        bean.doSomething();
    }

    public class MyBean {
        @Value("${myProp}")
        private String myPropValue;

        public void doSomething() {
            System.out.println("the value of myProp: " + myPropValue);
        }
    }
}