package com.rosariodev.videogames.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
 
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private double prize;

    

}
