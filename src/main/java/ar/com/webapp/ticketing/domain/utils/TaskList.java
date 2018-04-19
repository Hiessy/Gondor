package ar.com.webapp.ticketing.domain.utils;

import ar.com.webapp.ticketing.core.model.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskList {
    private List<Task> tasks = new ArrayList<Task>();
    private Long ticketId;

    public TaskList(List<Task> tasks, Long ticketId) {
        this.tasks = tasks;
        this.ticketId = ticketId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

}
