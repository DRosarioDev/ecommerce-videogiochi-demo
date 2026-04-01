package com.rosariodev.videogames.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {
    
    @NotBlank
    private String review;


    

}
