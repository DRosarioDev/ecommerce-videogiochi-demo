package com.rosariodev.videogames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.videogames.model.dto.OrderDTO;
import com.rosariodev.videogames.model.dto.PurchaseDTO;
import com.rosariodev.videogames.model.dto.UserLoginDTO;
import com.rosariodev.videogames.model.dto.UserRegisterDTO;
import com.rosariodev.videogames.service.ServiceUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private ServiceUser serviceUser;

    @PostMapping("/login")
    public String login(@NotNull @Valid @RequestBody UserLoginDTO userDTO){
        return serviceUser.login(userDTO);
    }

    @PostMapping("/register")
    public void register(@NotNull @Valid @RequestBody UserRegisterDTO userDTO){
        serviceUser.register(userDTO);
    }

    @GetMapping("/users")
    public List<UserLoginDTO> getAllUsers(){
        return serviceUser.getAllUsers();
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrders(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return serviceUser.getOrders(email);
    }

    @PostMapping("/add-chart")
    public Long addChart(@AuthenticationPrincipal UserDetails userDetails, @NotNull @Valid OrderDTO orderDTO){
        String email = userDetails.getUsername();
        return serviceUser.createOrder(orderDTO, email);
    }

    @GetMapping("/chart")
    public List<PurchaseDTO> getChart(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return serviceUser.getChart(email);
    }

    @DeleteMapping("/remove-product-chart")
    public void removeProductChart(@AuthenticationPrincipal UserDetails userDetails, @NotNull @Valid Long idProduct){
        String email = userDetails.getUsername();
        serviceUser.removeProductFromChart(idProduct, email);
    }

    @PostMapping("/checkout")
    public OrderDTO checkout(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return serviceUser.checkout(email);
    }

    
    
}
