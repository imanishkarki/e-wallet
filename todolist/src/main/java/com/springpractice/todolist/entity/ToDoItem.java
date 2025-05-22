package com.springpractice.todolist.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "todo_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItem {

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
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

     public String getAuthor() {
          return author;
     }

     public void setAuthor(String author) {
          this.author = author;
     }

     public LocalDateTime getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(LocalDateTime createdAt) {
          this.createdAt = createdAt;
     }

     public Boolean getCompleted() {
          return completed;
     }

     public void setCompleted(Boolean completed) {
          this.completed = completed;
     }

     public LocalDateTime getUpdatedAt() {
          return updatedAt;
     }

     public void setUpdatedAt(LocalDateTime updatedAt) {
          this.updatedAt = updatedAt;
     }

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String title;
     private String description;
     @Column(name="Owner")
     private String author;
     private Boolean completed;

     private LocalDateTime createdAt;

     private LocalDateTime updatedAt;

     @PrePersist
     protected void onCreate(){
         createdAt = updatedAt= LocalDateTime.now();
     }

     @PreUpdate
     protected  void onUpdate(){
         updatedAt = LocalDateTime.now();
     }
}
