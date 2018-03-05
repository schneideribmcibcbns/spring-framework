package com.logicbig.example;

public class TestBean2 {
    private TestBean testBean;
    private String anotherString;

    public TestBean getTestBean () {
        return testBean;
    }

    public void setTestBean (TestBean testBean) {
        this.testBean = testBean;
    }

    public String getAnotherString () {
        return anotherString;
    }

    public void setAnotherString (String anotherString) {
        this.anotherString = anotherString;
    }

    @Override
    public String toString () {
        return "TestBean2{" +
                            "testBean=" + testBean +
                            ", anotherString='" + anotherString + '\'' +
                            '}';
    }
}