package com.logicbig.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource(locations = "classpath:test.properties",
        properties = "report-subscriber=tester@example.com")
public class ReportServiceTests {
    @Autowired
    private ReportService reportService;

    @Test
    public void testReportSubscriber() {
        String s = reportService.getReportSubscriber();
        System.out.println(s);
        Assert.assertEquals("tester@example.com", s);
    }
}