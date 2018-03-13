# Annotation-driven Formatting

Spring provides a mechanism for declaratively configuring fields with formatting rules using annotations.

## Using Spring provided annotations

Spring provides predefined annotations in package: `org.springframework.format.annotation`.

Here's an example of using `DateTimeFormat` and `NumberFormat`:

### Creating a bean and using the formatting related annotations:

```java
public class Order{
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private Double price;

    @DateTimeFormat(pattern = "yyyy")
    private Date date;

   //getters and setters and toString() methods
}
```

### Populating the bean fields using DataBinder

Here we are going to create `ConversionService`, configuring it with the `AnnotationFormatterFactory` instances which binds the `DateTimeFormat` and `NumberFormat` annotations with their corresponding formatters, and then using `DataBinder` to populate the fields of the target bean.

```java
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
```

#### Output

```java
target=Order{price=0.027, date=Fri Jan 01 00:00:00 CST 2016}
org.springframework.validation.BindingResult.target=org.springframework.validation.BeanPropertyBindingResult: 0 errors
```

## Creating custom annotations for formatting

### Define a custom annotation

This annotation is going to provide custom formatting rules configuration for `Locale` field.

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocaleFormat {
      LocaleStyle style() default LocaleStyle.CountryDisplayName;
}
```

```java
public enum LocaleStyle {
    CountryDisplayName,
    ISO3Country,
    ISO3Language;
}
```

### Create a Formatter

Creating the formatter is similar to what we did in the last tutorial. This formatter has no knowledge of the annotation we created in the last step.

```java
public class LocaleFormatter implements Formatter<Locale> {
   private LocaleStyle localeStyle;

   public LocaleStyle getLocaleStyle () {
       return localeStyle;
   }

   public void setLocaleStyle (
                       LocaleStyle localeStyle) {
       this.localeStyle = localeStyle;
   }

   @Override
   public Locale parse(String text, Locale locale) throws ParseException {
       Optional<Locale> o = Arrays.stream(Locale.getAvailableLocales()).parallel()
                                  .filter(l -> this.localeByStylePredicate(l, text))
                                  .findAny();
       if (o.isPresent()) {
           return o.get();
       }
       return null;
   }

   @Override
   public String print(Locale object, Locale locale) {
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

   private boolean localeByStylePredicate(Locale locale, String text) {
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
```

### Binding annotation with formatter

Now we are going to bind the annotation and the formatter (created in the last two steps) together by implementing `AnnotationFormatterFactory`.

Spring on finding the annotation on a field, will look for annotation-formatter mapping, as provided by this factory, and then invoke the corresponding formatter to populate the value to the field.

```java
public class LocaleFormatAnnotationFormatterFactory implements
                    AnnotationFormatterFactory<LocaleFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(Locale.class));
    }

    @Override
    public Printer<?> getPrinter(LocaleFormat annotation,
                        Class<?> fieldType) {
        return getLocaleFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(LocaleFormat annotation,
                        Class<?> fieldType) {
        return getLocaleFormatter(annotation, fieldType);
    }

    private Formatter<?> getLocaleFormatter(LocaleFormat annotation,
                                                  Class<?> fieldType) {
        LocaleFormatter lf = new LocaleFormatter();
        lf.setLocaleStyle(annotation.style());
        return lf;
    }
}
```

### Creating the target bean

On the fields we are going to use our custom annotation:

```java
public class MyBean {
    @LocaleFormat(style = LocaleStyle.ISO3Language)
    private Locale myLocale;
   //getters and setters
}
```

### Registering the AnnotationFormatterFactory to DataService and Using DataBinder

This is similar to what we did in the Spring predefined annotation example above:

```java
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
}
```

**Output**

```shell
target=com.logicbig.example.CustomFormatAnnotationExample$MyBean@50c87b21
org.springframework.validation.BindingResult.target=org.springframework.validation.BeanPropertyBindingResult: 0 errors
Malaysia
```