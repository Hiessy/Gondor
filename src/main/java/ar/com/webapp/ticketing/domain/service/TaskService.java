package ar.com.webapp.ticketing.domain.service;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.domain.utils.TaskList;

public interface TaskService {

    Task findTask(Long taskId); // Returns the BlogEntry or null if it can't be found
    Task closeTask(Long taskId);
    Task modifyTask(Long taskId, Task data);
    TaskList findAllTasksWithDescription(String description);
    TaskList findAllTasks();
}
