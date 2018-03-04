package com.logicbig.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ProfileValueSourceConfiguration(MyProfileValueSource.class)
public class ServiceTests {

    @Autowired
    private ReportService reportService;

    @Test
    @IfProfileValue(name = "report.enabled", value = "true")
    public void testReport() {
        String s = reportService.getReport();
        System.out.println(s);
        Assert.assertEquals("some report", s);
    }
}