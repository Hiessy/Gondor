package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.Ticket;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

public class TicketResource extends ResourceSupport {

    private Long rid;
    private String owner;
    private String creator;
    private String area;
    private String priority;
    private String title;
    private String description;
    private String status;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }


    public Ticket toTicket() {
        return new Ticket(rid, owner, creator, area, priority, title,description, status, created, modified);
    }
}
