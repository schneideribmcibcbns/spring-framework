package com.logicbig.example;

public class MainScreenProperties {
    private int refreshRate;
    private int width;
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