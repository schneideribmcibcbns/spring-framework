package com.logicbig.example;

import java.util.*;

public class InMemoryUserService implements UserService {
    private Map<Long, User> userMap = new LinkedHashMap<>();

    @Override
    public void saveUser (User user) {
        if (user.getId() == null) {
            user.setId((long) (userMap.size() + 1));
        }
        userMap.put(user.getId(), user);

    }

    @Override
    public List<User> getAllUsers () {
        return new ArrayList<>(userMap.values());
    }
}