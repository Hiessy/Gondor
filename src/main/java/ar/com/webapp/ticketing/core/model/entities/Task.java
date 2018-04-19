package ar.com.webapp.ticketing.core.model.entities;

import ar.com.webapp.ticketing.core.validator.model.annotation.AcceptedStringValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String area;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime update;
    private LocalDateTime closed;
    @NotBlank
    @AcceptedStringValidation(acceptedValues={"OPEN","IN PROGRESS","CLOSED","PENDING"}, message ="Invalid ticket status")
    private String status;
    @ManyToOne
    private Ticket ticket;
    @NotBlank
    private String owner;

    public Task(Long id, @NotBlank String area, @NotBlank String description, @NotBlank LocalDateTime created, LocalDateTime update, LocalDateTime closed, @NotBlank String status, Ticket ticket, String owner) {
        this.id = id;
        this.area = area;
        this.description = description;
        this.created = created;
        this.update = update;
        this.closed = closed;
        this.status = status;
        this.ticket = ticket;
        this.owner = owner;
    }

    public Task(Long id, @NotBlank String area, @NotBlank String description, @NotBlank LocalDateTime created, LocalDateTime update, LocalDateTime closed, @NotBlank String status, String owner) {
        this.id = id;
        this.area = area;
        this.description = description;
        this.created = created;
        this.update = update;
        this.closed = closed;
        this.status = status;
        this.owner = owner;
    }

    public Task(Long id, String area, String description, LocalDateTime created, LocalDateTime update, LocalDateTime closed) {
        this.id = id;
        this.area = area;
        this.description = description;
        this.created = created;
        this.update = update;
        this.closed = closed;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(area, task.area) &&
                Objects.equals(description, task.description) &&
                Objects.equals(created, task.created) &&
                Objects.equals(update, task.update) &&
                Objects.equals(closed, task.closed) &&
                Objects.equals(status, task.status) &&
                Objects.equals(ticket, task.ticket) &&
                Objects.equals(owner, task.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, area, description, created, update, closed, status, ticket, owner);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", update=" + update +
                ", closed=" + closed +
                ", status='" + status + '\'' +
                ", ticket=" + ticket +
                ", owner='" + owner + '\'' +
                '}';
    }
}
