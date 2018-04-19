package ar.com.webapp.ticketing.domain.service.impl;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.core.repositories.TaskRepository;
import ar.com.webapp.ticketing.domain.service.TaskService;
import ar.com.webapp.ticketing.domain.service.exception.TaskDoesNotExistException;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task findTask(Long taskId) {
        return taskRepository.findTask(taskId);
    }

    @Override
    public Task closeTask(Long taskId) {
        return taskRepository.deleteTask(taskId);
    }

    @Override
    public Task modifyTask(Long taskId, Task data) {
        Task storedTask = taskRepository.findTask(taskId);
        if(storedTask == null)
            throw new TaskDoesNotExistException();


        return taskRepository.updateTask(updateStoredTask(storedTask,data));
    }

    @Override
    public TaskList findAllTasksWithDescription(String description) {
        return new TaskList(taskRepository.findAllTasksByDescription(description),null);
    }

    @Override
    public TaskList findAllTasks() {
        return new TaskList(taskRepository.findAllTasks(),null);
    }

    private Task updateStoredTask(Task storedTask, Task data) {
        if(data.getArea() != null)
            storedTask.setArea(data.getArea());
        if(data.getStatus() != null)
            storedTask.setStatus(data.getStatus());
        if(data.getDescription() != null)
            storedTask.setDescription(data.getDescription());
        if(data.getOwner() != null)
            storedTask.setOwner(data.getOwner());

        storedTask.setUpdate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return storedTask;
    }
}
