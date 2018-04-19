package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.Task;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class TaskListResource extends ResourceSupport {

    private List<TaskResource> tasks;

    public List<TaskResource> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResource> tasks) {
        this.tasks = tasks;
    }

    public List<Task> toTaskList() {
        List<Task> newTasks = new ArrayList<>();
        for (TaskResource res : tasks)
            newTasks.add(new Task(res.getRid(), res.getArea(), res.getDescription(), res.getCreated(), res.getExpectedClose(), res.getClosed(), res.getStatus(), res.getOwner()));

        return newTasks;
    }

}
