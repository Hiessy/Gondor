package ar.com.webapp.ticketing.domain.service.utils;

import ar.com.webapp.ticketing.core.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserList(List<User> users) {

        this.users = users;
    }
}
