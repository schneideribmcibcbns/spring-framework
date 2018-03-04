package com.logicbig.example;

public class CustomerScreenProperties {
    private String title;
    private String nameLabel;
    private String phoneLabel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    @Override
    public String toString() {
        return "CustomerScreenProperties{" +
                "title='" + title + '\'' +
                ", nameLabel='" + nameLabel + '\'' +
                ", phoneLabel='" + phoneLabel + '\'' +
                '}';
    }
}