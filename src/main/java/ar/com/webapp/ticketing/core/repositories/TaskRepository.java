package ar.com.webapp.ticketing.core.repositories;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.domain.utils.TaskList;

import java.util.List;

public interface TaskRepository {

    Task createTask(Task data);
    List<Task> findAllTasksForTicketId(Long ticketId);
    List<Task> findAllTasksForUser(String userName);

    Task findTask(Long taskId);

    Task deleteTask(Long taskId);

    Task updateTask(Task data);

    List<Task> findAllTasksByDescription(String description);

    List<Task> findAllTasks();
}
