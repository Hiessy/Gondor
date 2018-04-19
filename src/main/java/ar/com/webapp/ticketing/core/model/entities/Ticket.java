package ar.com.webapp.ticketing.core.model.entities;

import ar.com.webapp.ticketing.core.validator.model.annotation.AcceptedStringValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import java.util.Set;


@Entity
@Table
public class Ticket {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    private String owner;
    @NotBlank
    private String creator;
    @NotBlank
    private String area;
    @NotBlank
    @AcceptedStringValidation(acceptedValues={"CRITICAL","HIGH","NORMAL","LOW"}, message ="Invalid ticket priority")
    private String priority;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @AcceptedStringValidation(acceptedValues={"OPEN","IN PROGRESS","CLOSED","PENDING"}, message ="Invalid ticket status")
    private String status;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime modified;

    public Ticket(String owner, String creator, String area, String priority, String title, String description, String status, LocalDateTime created, LocalDateTime modified) {
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

    public Ticket(Long id, String owner, String creator, String area, String priority, String title, String description, String status, LocalDateTime created, LocalDateTime modified) {

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
}

