package com.springpractice.todolist.payload;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class ToDoItemDTO {
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

        private Long id;
        private String title;

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        private String author;
        @NotBlank(message = "Description is must.")
        @Length(min = 10, max = 254)
        private String description;
        public ToDoItemDTO(Long id, String title, String author, String description) {
                this.id = id;
                this.title = title;
                this.author = author;
                this.description = description;
        }

}
