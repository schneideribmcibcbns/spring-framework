package com.logicbig.example;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.util.*;

public class CustomFormatAnnotationExample {

    public static void main (String[] args) {
        DefaultFormattingConversionService service =
                            new DefaultFormattingConversionService();
        service.addFormatterForFieldAnnotation(
                            new LocaleFormatAnnotationFormatterFactory());

        MyBean bean = new MyBean();
        DataBinder dataBinder = new DataBinder(bean);
        dataBinder.setConversionService(service);

        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("myLocale", "msa");

        dataBinder.bind(mpv);
        dataBinder.getBindingResult()
                  .getModel()
                  .entrySet()
                  .forEach(System.out::println);
        System.out.println(bean.getMyLocale().getDisplayCountry());
    }

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LocaleFormat {
        LocaleStyle style () default LocaleStyle.CountryDisplayName;
    }

    public enum LocaleStyle {
        CountryDisplayName,
        ISO3Country,
        ISO3Language;
    }

    private static class LocaleFormatter implements Formatter<Locale> {
        private LocaleStyle localeStyle;

        public LocaleStyle getLocaleStyle () {
            return localeStyle;
        }

        public void setLocaleStyle (
                            LocaleStyle localeStyle) {
            this.localeStyle = localeStyle;
        }

        @Override
        public Locale parse (String text, Locale locale) throws ParseException {
            Optional<Locale> o = Arrays.stream(Locale.getAvailableLocales()).parallel()
                                       .filter(l -> this.localeByStylePredicate(l, text))
                                       .findAny();
            if (o.isPresent()) {
                return o.get();
            }
            return null;
        }

        @Override
        public String print (Locale object, Locale locale) {
            switch (localeStyle) {
                case CountryDisplayName:
                    return object.getDisplayCountry();
                case ISO3Country:
                    return object.getISO3Country();
                case ISO3Language:
                    return object.getISO3Language();
            }
            return object.toString();
        }

        private boolean localeByStylePredicate (Locale locale, String text) {
            try {
                switch (localeStyle) {
                    case CountryDisplayName:
                        return locale.getDisplayCountry().equalsIgnoreCase(text);
                    case ISO3Country:
                        return locale.getISO3Country().equalsIgnoreCase(text);
                    case ISO3Language:
                        return locale.getISO3Language().equalsIgnoreCase(text);
                }
            } catch (MissingResourceException e) {
                //ignore;
            }
            return false;
        }
    }


    private static class LocaleFormatAnnotationFormatterFactory implements
                        AnnotationFormatterFactory<LocaleFormat> {

        @Override
        public Set<Class<?>> getFieldTypes () {
            return new HashSet<>(Arrays.asList(Locale.class));
        }

        @Override
        public Printer<?> getPrinter (LocaleFormat annotation,
                            Class<?> fieldType) {
            return getLocaleFormatter(annotation, fieldType);
        }

        @Override
        public Parser<?> getParser (LocaleFormat annotation,
                            Class<?> fieldType) {
            return getLocaleFormatter(annotation, fieldType);
        }

        private Formatter<?> getLocaleFormatter (LocaleFormat annotation, Class<?> fieldType) {
            LocaleFormatter lf = new LocaleFormatter();
            lf.setLocaleStyle(annotation.style());
            return lf;
        }
    }

    private static class MyBean {
        @LocaleFormat(style = LocaleStyle.ISO3Language)
        private Locale myLocale;

        public Locale getMyLocale () {
            return myLocale;
        }

        public void setMyLocale (Locale myLocale) {
            this.myLocale = myLocale;
        }

    }

}