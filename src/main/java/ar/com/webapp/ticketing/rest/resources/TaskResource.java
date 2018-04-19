package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.Task;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

public class TaskResource extends ResourceSupport {

    private Long rid;
    private String area;
    private String description;
    private LocalDateTime created;
    private LocalDateTime expectedClose;
    private LocalDateTime closed;
    private String status;
    private String owner;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getExpectedClose() {
        return expectedClose;
    }

    public void setExpectedClose(LocalDateTime expectedClose) {
        this.expectedClose = expectedClose;
    }

    public LocalDateTime getClosed() {
        return closed;
    }

    public void setClosed(LocalDateTime closed) {
        this.closed = closed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task toTask() {

        return new Task(rid, area, description, created, expectedClose, closed, status, owner);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}
