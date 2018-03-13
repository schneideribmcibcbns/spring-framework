package com.logicbig.example;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;

import java.util.Date;

public class SpringFormatAnnotationExample {

    public static void main (String[] args) {

        DefaultFormattingConversionService conversionService =
                            new DefaultFormattingConversionService(false);

        conversionService.addFormatterForFieldAnnotation(
                            new NumberFormatAnnotationFormatterFactory());

        conversionService.addFormatterForFieldAnnotation(
                            new DateTimeFormatAnnotationFormatterFactory());

        Order order = new Order();
        DataBinder dataBinder = new DataBinder(order);
        dataBinder.setConversionService(conversionService);


        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("price", "2.7%");
        mpv.add("date", "2016");

        dataBinder.bind(mpv);
        dataBinder.getBindingResult()
                  .getModel()
                  .entrySet()
                  .forEach(System.out::println);
    }

    private static class Order {
        @NumberFormat(style = NumberFormat.Style.PERCENT)
        private Double price;

        @DateTimeFormat(pattern = "yyyy")
        private Date date;

        public Double getPrice () {
            return price;
        }

        public void setPrice (Double price) {
            this.price = price;
        }

        public Date getDate () {
            return date;
        }

        public void setDate (Date date) {
            this.date = date;
        }

        @Override
        public String toString () {
            return "Order{" +
                                "price=" + price +
                                ", date=" + date +
                                '}';
        }
    }
}