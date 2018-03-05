package com.logicbig.example;

import java.util.List;

public class TestBean3 {
    private List<TestBean> testBeans;

    public List<TestBean> getTestBeans () {
        return testBeans;
    }

    public void setTestBeans (List<TestBean> testBeans) {
        this.testBeans = testBeans;
    }

    public TestBean getTestBeans (int index) {
        return testBeans.get(index);
    }

    public void setTestBeans (int index, TestBean testBean) {
        this.testBeans.add(index, testBean);
    }

    @Override
    public String toString () {
        return "TestBean3{" +
                            "testBeans=" + testBeans +
                            '}';
    }
}