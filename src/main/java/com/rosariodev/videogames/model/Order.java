package com.rosariodev.videogames.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {
    
    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;
    @ManyToOne
    private User user;
    @ToString.Exclude
    @OneToMany(mappedBy = "order")
    private List<Purchase> listPurchase = new ArrayList<>();
    private Double total = 0.0;

    public Order(LocalDateTime data, User user) {
        this.data = data;
        this.user = user;
    }
    
    public Double getTotalPrice(){
        double total = 0;

        for (Purchase purchase : listPurchase) {
            for (int i = 0; i < purchase.getQuantity(); i++) {
                total += purchase.getProduct().getPrice();
            }
            
        }

        return total;

    }

    public void addPurchace(Purchase purchase){
        this.listPurchase.add(purchase);
    }

}
