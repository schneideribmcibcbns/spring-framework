# Field Formatting

Spring formatting API allows a UI/GUI application to render objects in formatted string. It also allows the opposite, that is to populate object fields in response to the user formatted input from a UI.

## Formatter interface

The Spring formatting API is based on the interface `Formatter`:

```java
 package org.springframework.format;

 public interface Formatter<T> extends Printer<T>, Parser<T> {
 }
```

`Formatter` interface allows us to convert a bean field of type `T` to String and vice versa.

Here are the `Formatter` super interfaces `Printer` and `Parser`

```java
package org.springframework.format;

import java.util.Locale;

 public interface Printer<T> {
   String print(T object, Locale locale);
}
```

```java
package org.springframework.format;

import java.text.ParseException;
import java.util.Locale;

public interface Parser<T> {
  T parse(String text, Locale locale) throws ParseException;
}
```

## Spring provided Formatter implementations

Spring provides various formatter implementations in the following packages:

* org.springframework.format.datetime provides formatters for type java.util.Date.

* org.springframework.format.datetime.joda provides formatters for Joda date and time types.

* org.springframework.format.datetime.standard: formatter for JSR-310 Java 8 java.time types.

* org.springframework.format.number: formatters for java.lang.Number sub types.

* org.springframework.format.number.money: formatters for JSR 354 javax.money. This package is not included in JSE yet and planned for Java 9 but it can be downloaded here.

The above formatter may be registered implicitly by Spring depending on what context we are using.

## Converter vs Formatter

Spring Converter API provides general purpose type conversion system. Converters do not address customized formatted field values rendering. Formatter API does that. Formatters can also localize String values while formatting e.g. rendering Date/LocalDate etc per client locale.

## ConversionService and Formatters

Other than invoking converters, `ConversionService` may invoke formatters as well. `ConversionService` does not define any specific methods related to formatting though, instead a `Formatter` may be implicitly invoked on `ConversionService#convert()` method call.

In a core Spring application we have to use `ConversionService` implementations like `FormattingConversionService` or `DefaultFormattingConversionService`. Both these implementations also implement `FormatterRegistry` interface which allows for user defined formatters to be registered:

```java
package org.springframework.format;

public interface FormatterRegistry extends ConverterRegistry {

    void addFormatterForFieldType(Class<?> fieldType, Printer<?> printer,
                                                               Parser<?> parser);

    void addFormatterForFieldType(Class<?> fieldType, Formatter<?> formatter);

    void addFormatterForFieldType(Formatter<?> formatter);

    void addFormatterForAnnotation(AnnotationFormatterFactory<?, ?> factory);
}
```

By default, `FormattingConversionService` does not register any of Spring provided formatters (as listed above).

`DefaultConversionService` registers most of those formatters by default. Here's a simple example:

```java
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.Instant;
import java.time.LocalDate;

public class DefaultFormattingConversionServiceExample {
    public static void main (String[] args) {

       ConversionService service =
                            new DefaultFormattingConversionService();

        //String to Instant
        Instant instant = service.convert("2016-11-15T01:12:05.695Z", Instant.class);
        System.out.println(instant);

        //Instant to String
        String convert = service.convert(instant, String.class);
        System.out.println(convert);

        LocalDate localDate = service.convert("11/13/16", LocalDate.class);
        System.out.println(localDate);
    }
}
```

### Output

```shell
2016-11-15T01:12:05.695Z
2016-11-15T01:12:05.695Z
2016-11-13
```

The above `service.convert()` calls use `InstantFormatter` (we are doing both sides formatting i.e. `Instant` to `String` and vice versa) and `TemporalAccessorParser` respectively.

## How to find what converters/formatters are registered by default?

Spring reference documents do not seem to provide a formal list of converters/formatters which are used by default. That may be because that list changes too frequently with each release. Also what formatters are registered depends on the context being used. The easiest and convenient way I found is to print the `ConversionService` instance:

```java
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

public class DefaultFormatterListExample {
    public static void main (String[] args) {

       ConversionService service =
                            new DefaultFormattingConversionService();
        System.out.println(service);
  }
}
```

### Output

```shell
ConversionService converters =
@org.springframework.format.annotation.DateTimeFormat java.lang.Long -> java.lang.String: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c,@org.springframework.format.annotation.NumberFormat java.lang.Long -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.DateTimeFormat java.time.LocalDate -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.LocalDate -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@11531931
@org.springframework.format.annotation.DateTimeFormat java.time.LocalDateTime -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.LocalDateTime -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@4cdbe50f
@org.springframework.format.annotation.DateTimeFormat java.time.LocalTime -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.LocalTime -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@1fbc7afb
@org.springframework.format.annotation.DateTimeFormat java.time.OffsetDateTime -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.OffsetDateTime -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@6debcae2
@org.springframework.format.annotation.DateTimeFormat java.time.OffsetTime -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.OffsetTime -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@2ff4f00f
@org.springframework.format.annotation.DateTimeFormat java.time.ZonedDateTime -> java.lang.String: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.time.ZonedDateTime -> java.lang.String : org.springframework.format.datetime.standard.TemporalAccessorPrinter@7cf10a6f
@org.springframework.format.annotation.DateTimeFormat java.util.Calendar -> java.lang.String: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c
@org.springframework.format.annotation.DateTimeFormat java.util.Date -> java.lang.String: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c
@org.springframework.format.annotation.NumberFormat java.lang.Byte -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.lang.Double -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.lang.Float -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.lang.Integer -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.lang.Short -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.math.BigDecimal -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
@org.springframework.format.annotation.NumberFormat java.math.BigInteger -> java.lang.String: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.Boolean -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@50cbc42f
java.lang.Character -> java.lang.Number : org.springframework.core.convert.support.CharacterToNumberFactory@5f150435
java.lang.Character -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@71e7a66b
java.lang.Enum -> java.lang.Integer : org.springframework.core.convert.support.EnumToIntegerConverter@f5f2bb7
java.lang.Enum -> java.lang.String : org.springframework.core.convert.support.EnumToStringConverter@282ba1e
java.lang.Integer -> java.lang.Enum : org.springframework.core.convert.support.IntegerToEnumConverterFactory@13b6d03
java.lang.Long -> java.time.Instant : org.springframework.format.datetime.standard.DateTimeConverters$LongToInstantConverter@71c7db30
java.lang.Long -> java.util.Calendar : org.springframework.format.datetime.DateFormatterRegistrar$LongToCalendarConverter@725bef66,java.lang.Long -> java.util.Calendar : org.springframework.format.datetime.DateFormatterRegistrar$LongToCalendarConverter@2aaf7cc2
java.lang.Long -> java.util.Date : org.springframework.format.datetime.DateFormatterRegistrar$LongToDateConverter@6ad5c04e,java.lang.Long -> java.util.Date : org.springframework.format.datetime.DateFormatterRegistrar$LongToDateConverter@6833ce2c
java.lang.Number -> java.lang.Character : org.springframework.core.convert.support.NumberToCharacterConverter@2ac1fdc4
java.lang.Number -> java.lang.Number : org.springframework.core.convert.support.NumberToNumberConverterFactory@1ff8b8f
java.lang.Number -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@224aed64
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.lang.Long: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c,java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Long: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.LocalDate: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.LocalDate: org.springframework.format.datetime.standard.TemporalAccessorParser@5e025e70
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.LocalDateTime: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.LocalDateTime: org.springframework.format.datetime.standard.TemporalAccessorParser@66d33a
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.LocalTime: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.LocalTime: org.springframework.format.datetime.standard.TemporalAccessorParser@45c8e616
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.OffsetDateTime: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.OffsetDateTime: org.springframework.format.datetime.standard.TemporalAccessorParser@5ba23b66
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.OffsetTime: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.OffsetTime: org.springframework.format.datetime.standard.TemporalAccessorParser@c818063
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.time.ZonedDateTime: org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory@4563e9ab,java.lang.String -> java.time.ZonedDateTime: org.springframework.format.datetime.standard.TemporalAccessorParser@7e0babb1
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.util.Calendar: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c
java.lang.String -> @org.springframework.format.annotation.DateTimeFormat java.util.Date: org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory@2aafb23c
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Byte: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Double: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Float: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Integer: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.lang.Short: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.math.BigDecimal: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> @org.springframework.format.annotation.NumberFormat java.math.BigInteger: org.springframework.format.number.NumberFormatAnnotationFormatterFactory@34340fab
java.lang.String -> java.lang.Boolean : org.springframework.core.convert.support.StringToBooleanConverter@1c53fd30
java.lang.String -> java.lang.Character : org.springframework.core.convert.support.StringToCharacterConverter@c39f790
java.lang.String -> java.lang.Enum : org.springframework.core.convert.support.StringToEnumConverterFactory@75412c2f
java.lang.String -> java.lang.Number : org.springframework.core.convert.support.StringToNumberConverterFactory@387c703b
java.lang.String -> java.nio.charset.Charset : org.springframework.core.convert.support.StringToCharsetConverter@3ecf72fd
java.lang.String -> java.time.Duration: org.springframework.format.datetime.standard.DurationFormatter@7d417077
java.lang.String -> java.time.Instant: org.springframework.format.datetime.standard.InstantFormatter@3f0ee7cb
java.lang.String -> java.time.MonthDay: org.springframework.format.datetime.standard.MonthDayFormatter@35bbe5e8
java.lang.String -> java.time.Period: org.springframework.format.datetime.standard.PeriodFormatter@75bd9247
java.lang.String -> java.time.YearMonth: org.springframework.format.datetime.standard.YearMonthFormatter@7dc36524
java.lang.String -> java.util.Currency : org.springframework.core.convert.support.StringToCurrencyConverter@21a06946
java.lang.String -> java.util.Locale : org.springframework.core.convert.support.StringToLocaleConverter@73035e27
java.lang.String -> java.util.Properties : org.springframework.core.convert.support.StringToPropertiesConverter@326de728
java.lang.String -> java.util.TimeZone : org.springframework.core.convert.support.StringToTimeZoneConverter@47fd17e3
java.lang.String -> java.util.UUID : org.springframework.core.convert.support.StringToUUIDConverter@7a92922
java.nio.charset.Charset -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@483bf400
java.time.Duration -> java.lang.String : org.springframework.format.datetime.standard.DurationFormatter@7d417077
java.time.Instant -> java.lang.Long : org.springframework.format.datetime.standard.DateTimeConverters$InstantToLongConverter@19bb089b
java.time.Instant -> java.lang.String : org.springframework.format.datetime.standard.InstantFormatter@3f0ee7cb
java.time.LocalDateTime -> java.time.LocalDate : org.springframework.format.datetime.standard.DateTimeConverters$LocalDateTimeToLocalDateConverter@6e3c1e69
java.time.LocalDateTime -> java.time.LocalTime : org.springframework.format.datetime.standard.DateTimeConverters$LocalDateTimeToLocalTimeConverter@1888ff2c
java.time.MonthDay -> java.lang.String : org.springframework.format.datetime.standard.MonthDayFormatter@35bbe5e8
java.time.OffsetDateTime -> java.time.Instant : org.springframework.format.datetime.standard.DateTimeConverters$OffsetDateTimeToInstantConverter@23223dd8
java.time.OffsetDateTime -> java.time.LocalDate : org.springframework.format.datetime.standard.DateTimeConverters$OffsetDateTimeToLocalDateConverter@256216b3
java.time.OffsetDateTime -> java.time.LocalDateTime : org.springframework.format.datetime.standard.DateTimeConverters$OffsetDateTimeToLocalDateTimeConverter@d7b1517
java.time.OffsetDateTime -> java.time.LocalTime : org.springframework.format.datetime.standard.DateTimeConverters$OffsetDateTimeToLocalTimeConverter@2a18f23c
java.time.OffsetDateTime -> java.time.ZonedDateTime : org.springframework.format.datetime.standard.DateTimeConverters$OffsetDateTimeToZonedDateTimeConverter@16c0663d
java.time.Period -> java.lang.String : org.springframework.format.datetime.standard.PeriodFormatter@75bd9247
java.time.YearMonth -> java.lang.String : org.springframework.format.datetime.standard.YearMonthFormatter@7dc36524
java.time.ZoneId -> java.util.TimeZone : org.springframework.core.convert.support.ZoneIdToTimeZoneConverter@7cdbc5d3
java.time.ZonedDateTime -> java.time.Instant : org.springframework.format.datetime.standard.DateTimeConverters$ZonedDateTimeToInstantConverter@28f67ac7
java.time.ZonedDateTime -> java.time.LocalDate : org.springframework.format.datetime.standard.DateTimeConverters$ZonedDateTimeToLocalDateConverter@35851384
java.time.ZonedDateTime -> java.time.LocalDateTime : org.springframework.format.datetime.standard.DateTimeConverters$ZonedDateTimeToLocalDateTimeConverter@6adca536
java.time.ZonedDateTime -> java.time.LocalTime : org.springframework.format.datetime.standard.DateTimeConverters$ZonedDateTimeToLocalTimeConverter@649d209a
java.time.ZonedDateTime -> java.time.OffsetDateTime : org.springframework.format.datetime.standard.DateTimeConverters$ZonedDateTimeToOffsetDateTimeConverter@357246de
java.time.ZonedDateTime -> java.util.Calendar : org.springframework.core.convert.support.ZonedDateTimeToCalendarConverter@3aa9e816
java.util.Calendar -> java.lang.Long : org.springframework.format.datetime.DateFormatterRegistrar$CalendarToLongConverter@28864e92,java.util.Calendar -> java.lang.Long : org.springframework.format.datetime.DateFormatterRegistrar$CalendarToLongConverter@6ea6d14e
java.util.Calendar -> java.time.Instant : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToInstantConverter@5a01ccaa
java.util.Calendar -> java.time.LocalDate : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToLocalDateConverter@ea4a92b
java.util.Calendar -> java.time.LocalDateTime : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToLocalDateTimeConverter@47f37ef1
java.util.Calendar -> java.time.LocalTime : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToLocalTimeConverter@3c5a99da
java.util.Calendar -> java.time.OffsetDateTime : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToOffsetDateTimeConverter@1b40d5f0
java.util.Calendar -> java.time.ZonedDateTime : org.springframework.format.datetime.standard.DateTimeConverters$CalendarToZonedDateTimeConverter@4ec6a292
java.util.Calendar -> java.util.Date : org.springframework.format.datetime.DateFormatterRegistrar$CalendarToDateConverter@546a03af,java.util.Calendar -> java.util.Date : org.springframework.format.datetime.DateFormatterRegistrar$CalendarToDateConverter@721e0f4f
java.util.Currency -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@77f03bb1
java.util.Date -> java.lang.Long : org.springframework.format.datetime.DateFormatterRegistrar$DateToLongConverter@2b80d80f,java.util.Date -> java.lang.Long : org.springframework.format.datetime.DateFormatterRegistrar$DateToLongConverter@3ab39c39
java.util.Date -> java.util.Calendar : org.springframework.format.datetime.DateFormatterRegistrar$DateToCalendarConverter@2eee9593,java.util.Date -> java.util.Calendar : org.springframework.format.datetime.DateFormatterRegistrar$DateToCalendarConverter@7907ec20
java.util.Locale -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@64c64813
java.util.Properties -> java.lang.String : org.springframework.core.convert.support.PropertiesToStringConverter@25618e91
java.util.UUID -> java.lang.String : org.springframework.core.convert.support.ObjectToStringConverter@71f2a7d5
org.springframework.core.convert.support.ArrayToArrayConverter@4b6995df
org.springframework.core.convert.support.ArrayToCollectionConverter@2cfb4a64
org.springframework.core.convert.support.ArrayToObjectConverter@445b84c0
org.springframework.core.convert.support.ArrayToStringConverter@66048bfd
org.springframework.core.convert.support.ByteBufferConverter@34033bd0
org.springframework.core.convert.support.ByteBufferConverter@34033bd0
org.springframework.core.convert.support.ByteBufferConverter@34033bd0
org.springframework.core.convert.support.ByteBufferConverter@34033bd0
org.springframework.core.convert.support.CollectionToArrayConverter@5474c6c
org.springframework.core.convert.support.CollectionToCollectionConverter@2fc14f68
org.springframework.core.convert.support.CollectionToObjectConverter@7bb11784
org.springframework.core.convert.support.CollectionToStringConverter@233c0b17
org.springframework.core.convert.support.FallbackObjectToStringConverter@1ae369b7
org.springframework.core.convert.support.IdToEntityConverter@17d99928,org.springframework.core.convert.support.ObjectToObjectConverter@3834d63f
org.springframework.core.convert.support.MapToMapConverter@591f989e
org.springframework.core.convert.support.ObjectToArrayConverter@61a52fbd
org.springframework.core.convert.support.ObjectToCollectionConverter@33a10788
org.springframework.core.convert.support.ObjectToOptionalConverter@6fffcba5
org.springframework.core.convert.support.StreamConverter@7006c658
org.springframework.core.convert.support.StreamConverter@7006c658
org.springframework.core.convert.support.StreamConverter@7006c658
org.springframework.core.convert.support.StreamConverter@7006c658
org.springframework.core.convert.support.StringToArrayConverter@61443d8f
org.springframework.core.convert.support.StringToCollectionConverter@63d4e2ba
```

## Using formatters with ApplicationContext

In core application context we have to inject `ConversionService` instance (configured with formatters) as a bean as we discussed [here](../06-conversion-service/README.md)

## Using formatters with DataBinder

We can use a `ConversionService` which supports formatting and set it to `DataBinder` as shown [here](../06-conversion-service/README.md).

## Creating custom Formatter

Similar to creating a custom `Converter` and registering with a `ConversionService`, we can also create custom formatters, register it with a `ConversionService` and inject the `ConversionService` as a bean.

An example, please see `CustomFormatterExample.java`.

Please also check out our [custom Formatter example in a MVC application](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-define-formatter.html).

