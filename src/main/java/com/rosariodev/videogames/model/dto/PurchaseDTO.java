package com.rosariodev.videogames.model.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    
    private Long id;
    private ProductDTO product;
    private int quantity;
    

    

}
