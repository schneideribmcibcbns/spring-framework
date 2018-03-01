package com.logicbig.example.dependson;


public class EventListener {

    private void initialize() {

        EventManager.getInstance().
                addListener(s ->
                        System.out.println("event received in EventListener : " + s));

    }
}