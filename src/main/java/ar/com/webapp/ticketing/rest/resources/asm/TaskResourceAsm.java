package ar.com.webapp.ticketing.rest.resources.asm;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.rest.controller.TaskController;
import ar.com.webapp.ticketing.rest.controller.TicketController;
import ar.com.webapp.ticketing.rest.resources.TaskResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TaskResourceAsm extends ResourceAssemblerSupport<Task, TaskResource>{

    public TaskResourceAsm() {
        super(TaskController.class, TaskResource.class);
    }

    @Override
    public TaskResource toResource(Task task) {
        TaskResource taskResource = new TaskResource();
        taskResource.setArea(task.getArea());
        taskResource.setRid(task.getId());
        taskResource.setClosed(task.getClosed());
        taskResource.setDescription(task.getDescription());
        taskResource.setCreated(task.getCreated());
        taskResource.setExpectedClose(task.getUpdate());
        taskResource.setOwner(task.getOwner());
        taskResource.setStatus(task.getStatus());
        taskResource.add(linkTo(TaskController.class).slash(task.getId()).withSelfRel());
        taskResource.add(linkTo(TaskController.class).slash(task.getArea()).withRel("area"));

        if(task.getTicket() != null)
            taskResource.add(linkTo(TicketController.class).slash(task.getTicket().getId()).withRel("ticket"));


        return taskResource;
    }
}
