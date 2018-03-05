package com.logicbig.example;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class UserList {
    private List<User> users;

    public List<User> getUsers () {
        return users;
    }

    public void setUsers (List<User> users) {
        this.users = users;
    }
}