package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Task")
public class Task {
    public Task(){this.createdTime = LocalTime.now();}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "Title", length = 50, nullable = false )
    private String title;

    @Column(name = "Description", length = 500, nullable = true)
    private String description;

    @Column(name = "DueDate", length = 50, nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 50)
    private PriorityLevel priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private TaskStatus status;

    @Column(name = "created_time", updatable = false)
    @CreationTimestamp
    private  LocalTime createdTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime)
    {
        this.createdTime = createdTime;
    }


}
