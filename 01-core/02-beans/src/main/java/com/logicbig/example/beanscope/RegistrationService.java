package com.logicbig.example.beanscope;

public interface RegistrationService {

    /**
     * In real application there should be a async call back
     *
     * @param userInfo
     */
    void register(UserInfo userInfo);
}