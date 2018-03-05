package com.logicbig.example;

public class Report {
    private int id;
    private String reportName;
    private String content;

    public String getReportName() {
        return reportName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportName='" + reportName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}