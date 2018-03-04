package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Value("${report-subscriber:admin@example.com}")
    private String theSubscriber;

    public String getReportSubscriber() {
        return theSubscriber;
    }
}