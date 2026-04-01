package com.rosariodev.videogames.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewOutputDTO {
    
    @NotBlank
    private UserOutputDTO user;
    @NotBlank
    private String review;


    

}
