package ar.com.webapp.ticketing.domain.utils;

import ar.com.webapp.ticketing.core.model.entities.Task;

import java.util.ArrayList;
import java.util.List;


public class UserTaskList {

    private List<Task> tasks = new ArrayList<Task>();
    private String userName;

    public UserTaskList(List<Task> tasks, String userName) {
        this.tasks = tasks;
        this.userName = userName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
