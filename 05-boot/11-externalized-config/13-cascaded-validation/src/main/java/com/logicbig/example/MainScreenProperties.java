package com.logicbig.example;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MainScreenProperties {
    @Min(1)
    private int refreshRate;
    @Min(50)
    @Max(1000)
    private int width;
    @Min(50)
    @Max(600)
    private int height;

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MainScreenProperties{" +
                "refreshRate=" + refreshRate +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}