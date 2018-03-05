package com.logicbig.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {
    private List<Report> reports = new ArrayList<>();

    @RequestMapping(value = "/reports",
            method = RequestMethod.POST,
            consumes = "text/report")
    @ResponseBody
    public String handleRequest(@RequestBody Report report) {
        report.setId(reports.size() + 1);
        reports.add(report);
        return "report saved: " + report;
    }

    @RequestMapping(
            value = "/reports/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public Report reportById(@PathVariable("id") int reportId) {
        if (reportId > reports.size()) {
            throw new RuntimeException("No found for the id :" + reportId);
        }
        return reports.get(reportId - 1);
    }
}