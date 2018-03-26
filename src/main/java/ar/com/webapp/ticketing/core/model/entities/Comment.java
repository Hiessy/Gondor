package ar.com.webapp.ticketing.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;
    private String authorUserName;
    private LocalDateTime posted;
    private String text;

    public Comment(Long id, String authorUserName, LocalDateTime posted, String text) {
        this.id = id;
        this.authorUserName = authorUserName;
        this.posted = posted;
        this.text = text;

    }


    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
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
                "id='" + id + '\'' +
                ", authorUserName='" + authorUserName + '\'' +
                ", posted=" + posted +
                ", text='" + text + '\'' +
                '}';
    }


}


