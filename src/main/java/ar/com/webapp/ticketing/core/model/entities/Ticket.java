package ar.com.webapp.ticketing.core.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Ticket {

    @Id @GeneratedValue
    private Long id;
    private String owner;
    private String creator;
    private String area;
    private String priority;
    private String title;
    private String description;
    private String status;
    private LocalDateTime created;
    private LocalDateTime modified;

    public Ticket(Long id, String owner, String creator, String area, String priority, String title ,String description, String status, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.owner = owner;
        this.creator = creator;
        this.area = area;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.status = status;
        this.created = created;
        this.modified = modified;
    }

    public Ticket() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", creator='" + creator + '\'' +
                ", area='" + area + '\'' +
                ", priority='" + priority + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}

