package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.domain.utils.TaskList;
import ar.com.webapp.ticketing.domain.utils.UserTaskList;
import ar.com.webapp.ticketing.rest.controller.TaskController;
import ar.com.webapp.ticketing.rest.resources.TaskListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class TaskListResourceAsm  extends ResourceAssemblerSupport<TaskList, TaskListResource> {

    public TaskListResourceAsm() {
        super(TaskController.class, TaskListResource.class);
    }

    @Override
    public TaskListResource toResource(TaskList taskSet) {
        TaskListResource finalResource = new TaskListResource();

        finalResource.setTasks(new TaskResourceAsm().toResources(taskSet.getTasks()));
        return finalResource;
    }

    public TaskListResource toResource(UserTaskList userTasksList) {
        TaskListResource finalResource = new TaskListResource();

        finalResource.setTasks(new TaskResourceAsm().toResources(userTasksList.getTasks()));
        return finalResource;
    }
}


