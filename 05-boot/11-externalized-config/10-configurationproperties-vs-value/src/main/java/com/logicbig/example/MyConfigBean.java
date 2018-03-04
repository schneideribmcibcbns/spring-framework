package com.logicbig.example;

import java.util.Currency;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfigBean {
	  @Value("${app.refresh-rate}")
	  private int refreshRate;
	  @Value("${app.refresh-time-unit}")
	  private TimeUnit refreshTimeUnit;
	  @Value("${app.trade-currency}")
	  private Currency tradeCurrency;
}
