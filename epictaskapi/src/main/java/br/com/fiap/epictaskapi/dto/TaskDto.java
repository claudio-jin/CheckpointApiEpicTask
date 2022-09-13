package br.com.fiap.epictaskapi.dto;

public record TaskDto (
     String title,
     String description,
     int score,
     int status

) {

     
     public TaskDto(String title, String description){
          this(title, description, 100, 0);
     }

}
