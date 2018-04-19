package ar.com.webapp.ticketing.rest.controller;

import ar.com.webapp.ticketing.core.model.entities.Task;
import ar.com.webapp.ticketing.domain.service.TaskService;
import ar.com.webapp.ticketing.domain.utils.TaskList;
import ar.com.webapp.ticketing.rest.resources.TaskListResource;
import ar.com.webapp.ticketing.rest.resources.TaskResource;
import ar.com.webapp.ticketing.rest.resources.asm.TaskListResourceAsm;
import ar.com.webapp.ticketing.rest.resources.asm.TaskResourceAsm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle tasks request from the view.
 * The controller will communicate with the service layer through the TaskService
 * in order to fulfil the request
 *
 * @author Martín Díaz
 */
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private final static Logger LOGGER = LogManager.getLogger(TaskController.class);
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * <p>GET method to find all tasks that have been created. There is an option to user a param with a description
     * in which case a search for the tasks with the description will be retrieved.</p>
     *
     * @param String description is not required
     * @return TaskListResource with all the tasks or all the tasks with a description.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TaskListResource> getAllTask(@RequestParam(value = "description", required = false) String description) {

        TaskList list = null;
        if (description != null) {

            list = taskService.findAllTasksWithDescription(description);
            LOGGER.info("Searching for all tasks with description matching: " + description + ".");
        } else {
            list = taskService.findAllTasks();
            LOGGER.info("Searching for all tasks created.");
        }
        if (list == null) {
            LOGGER.error("No tasks were found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info("All tasks where found successfully.");
        TaskListResource res = new TaskListResourceAsm().toResource(list);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * <p>GET method to search for a specific task.</p>
     *
     * @param Long taskId is the id that was assigned to the task upon creation
     * @return TaskResource with the required task information.
     */
    @RequestMapping(value = {"/{taskId}"}, method = RequestMethod.GET)
    public ResponseEntity<TaskResource> getTaskbyId(@PathVariable Long taskId) {
        LOGGER.info("Search for a task with task id " + taskId.toString() + ".");
        Task task = taskService.findTask(taskId);
        if (task != null) {
            LOGGER.info("Task was found successfully.");
            TaskResource res = new TaskResourceAsm().toResource(task);
            return new ResponseEntity<TaskResource>(res, HttpStatus.OK);
        } else {
            LOGGER.error("The task was not found.");
            return new ResponseEntity<TaskResource>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>DELETE method to close a task, this will not remove data.</p>
     *
     * @param Long taskId parameter is required to find out exactly what task to delete.
     * @return TaskResource with all the necessary data
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<TaskResource> closeTask(@PathVariable Long taskId) {
        LOGGER.info("Closing task with task id " + taskId.toString() + ".");
        Task task = taskService.closeTask(taskId);
        if (task != null) {
            LOGGER.info("The task was closed successfully.");
            TaskResource res = new TaskResourceAsm().toResource(task);
            return new ResponseEntity<TaskResource>(res, HttpStatus.OK);
        } else {
            LOGGER.error("The task was not found.");
            return new ResponseEntity<TaskResource>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>PUT method to make changes to a specific task with id.</p>
     *
     * @param Long         taskId identifying the task to change.
     * @param TaskResource task values that have to be updated.
     * @return
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<TaskResource> updateTask(@PathVariable Long taskId, @RequestBody TaskResource task) {
        LOGGER.info("Updating task with id " + taskId.toString() + ", with task data " + task.toString() + ".");
        Task modifiedTask = taskService.modifyTask(taskId, task.toTask());
        if (modifiedTask != null) {
            LOGGER.info("Updating task successfully.");
            TaskResource res = new TaskResourceAsm().toResource(modifiedTask);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            LOGGER.error("The task was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


}
