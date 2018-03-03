package com.logicbig.example;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserService implements UserService {
    private Map<Long, User> userMap = new HashMap<>();

    @Override
    public void saveUser (User user) {
        if (user.getId() == null) {
            user.setId((long) (userMap.size() + 1));
        }
        userMap.put(user.getId(), user);
    }
}