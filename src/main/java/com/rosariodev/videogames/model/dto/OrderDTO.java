package com.rosariodev.videogames.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
    

    private Long id;
    @NotNull
    private LocalDateTime data;
    @NotEmpty
    @Valid
    private List<PurchaseDTO> listPurchase = new ArrayList<>();
    private Double total;
}
