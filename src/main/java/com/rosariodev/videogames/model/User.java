package com.rosariodev.videogames.model;

import java.util.ArrayList;
import java.util.List;

import com.rosariodev.videogames.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {
    
    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
    
    public User(String name, String surname, String email, String password, ERole role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void addPurchase(Purchase purchase){
        this.purchases.add(purchase);
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    public boolean isProductPurchased(Product product){
        for (Order order : orders) {
            for (Purchase purchase : order.getListPurchase()) {
                if (purchase.getProduct().getId().equals(product.getId()) && order.getUser().getId() == getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public boolean hasPurchasedProduct(Product product){
        for (Order order : orders) {
            for (Purchase purchase : order.getListPurchase()) {
                if (purchase.getProduct().getId().equals(product.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
