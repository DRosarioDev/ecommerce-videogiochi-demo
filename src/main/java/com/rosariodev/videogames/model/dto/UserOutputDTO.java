package com.rosariodev.videogames.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserOutputDTO {
    
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    

}
