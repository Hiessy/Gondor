package ar.com.webapp.ticketing.core.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
public class Comment {

    @Id @GeneratedValue
    private Long id;
    @NotNull
    @OneToOne
    private User user;
    @NotNull
    private LocalDateTime posted;
    @NotBlank
    private String text;
    @NotNull
    @ManyToOne
    private Ticket ticket;

    public Comment() {
    }

    public Comment(Long id, @NotBlank LocalDateTime posted, @NotBlank String text) {
        this.id = id;
        this.posted = posted;
        this.text = text;
    }

    public Comment(Long id, @NotBlank User user, @NotBlank LocalDateTime posted, @NotBlank String text) {
        this.id = id;
        this.user = user;
        this.posted = posted;
        this.text = text;
    }

    public Comment(Long id, @NotBlank User user, @NotBlank LocalDateTime posted, @NotBlank String text, @NotBlank Ticket ticket) {
        this.id = id;
        this.user = user;
        this.posted = posted;
        this.text = text;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", posted=" + posted +
                ", text='" + text + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}


