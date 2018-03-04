package com.logicbig.example;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public String getReport() {
        return "some report";
    }
}