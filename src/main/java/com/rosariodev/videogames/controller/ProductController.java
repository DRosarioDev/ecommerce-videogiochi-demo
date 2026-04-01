package com.rosariodev.videogames.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.videogames.model.dto.ProductDTO;
import com.rosariodev.videogames.model.dto.ReviewDTO;
import com.rosariodev.videogames.model.dto.ReviewOutputDTO;
import com.rosariodev.videogames.service.ServiceProduct;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ServiceProduct serviceProduct;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        return serviceProduct.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProductByID(Long id){
        return serviceProduct.getProductByID(id);
    }       

    @GetMapping("/products/search")
    public List<ProductDTO> getProductsByName(@NotNull @Valid String name){
        return serviceProduct.getProductsByName(name);
    }

    @GetMapping("/products/reviews")
    public List<ReviewOutputDTO> getReviewsByPdoductID(@RequestParam @Valid Long idProduct){
        return serviceProduct.getReviewsByPdoductID(idProduct);
    }
    
    @PostMapping("/products/review/{idProduct}")
    public void writeReview(@AuthenticationPrincipal UserDetails userDetails, @NotNull @Valid @PathVariable Long idProduct, @NotNull @RequestBody ReviewDTO review){
        
        String email = userDetails.getUsername();
        serviceProduct.writeReview(email, idProduct, review);
    }

}
