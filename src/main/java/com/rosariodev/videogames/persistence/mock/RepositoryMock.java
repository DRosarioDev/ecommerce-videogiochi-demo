package com.rosariodev.videogames.persistence.mock;


import java.time.LocalDateTime;

import com.rosariodev.videogames.enums.ERole;
import com.rosariodev.videogames.model.Order;
import com.rosariodev.videogames.model.Product;
import com.rosariodev.videogames.model.Purchase;
import com.rosariodev.videogames.model.User;

public class RepositoryMock extends RepositoryGenericMock {

    private static final RepositoryMock singleton = new RepositoryMock();


    private RepositoryMock() {
        User user1 = new User("Mario", "Rossi", "user@a.it", "1234", ERole.USER);
        saveOrUpdate(user1);
        User user2 = new User("Giuseppe", "Verdi", "u2@a.it", "1235", ERole.USER);
        saveOrUpdate(user2);
        User user3 = new User("Francesco", "Bianchi", "u3@a.it", "1236", ERole.USER);
        saveOrUpdate(user3);

        Product product1 = new Product("Fifa 2024", "Il miglior gioco di calcio", 59.99);
        saveOrUpdate(product1);
        Product product2 = new Product("Call of Duty: Modern Warfare II", "Il miglior gioco di guerra", 69.99);
        saveOrUpdate(product2);
        Product product3 = new Product("The Legend of Zelda: Tears of the Kingdom", "Il miglior gioco di avventura", 79.99);
        saveOrUpdate(product3);
        Product product4 = new Product("Minecraft", "Il miglior gioco di costruzione", 29.99);
        saveOrUpdate(product4);
        Product product5 = new Product("Grand Theft Auto V", "Il miglior gioco di azione", 49.99);
        saveOrUpdate(product5);
        Product product6 = new Product("Cyberpunk 2077", "Il miglior gioco di fantascienza", 59.99);
        saveOrUpdate(product6);
        Product product7 = new Product("Red Dead Redemption 2", "Il miglior gioco di western", 69.99);
        saveOrUpdate(product7);
        Product product8 = new Product("The Witcher 3: Wild Hunt", "Il miglior gioco di ruolo", 39.99);
        saveOrUpdate(product8);
        Product product9 = new Product("Among Us", "Il miglior gioco di social deduction", 4.99);
        saveOrUpdate(product9);
        Product product10 = new Product("Hades", "Il miglior gioco di roguelike", 24.99);
        saveOrUpdate(product10);


        Order order1 = new Order(LocalDateTime.of(2025, 5, 5, 10, 37), user1);
        
        
        Purchase purchase11 = new Purchase(user1, product1, order1, 1);
        saveOrUpdate(purchase11);
        
        
        Purchase purchase12 = new Purchase(user1, product2, order1, 2);
        saveOrUpdate(purchase12);
        

        Purchase purchase13 = new Purchase(user1, product3, order1, 1);
        saveOrUpdate(purchase13);

        order1.addPurchace(purchase11);
        order1.addPurchace(purchase12);
        order1.addPurchace(purchase13);
        saveOrUpdate(order1);

        user1.addOrder(order1);

        Purchase purchase21 = new Purchase(user2, product6, order1, 1);
        saveOrUpdate(purchase21);
        Purchase purchase22 = new Purchase(user2, product1, order1, 3);
        saveOrUpdate(purchase22);
    
        
        Order order2 = new Order(LocalDateTime.of(2024, 3, 5, 11, 40), user2);
        order2.addPurchace(purchase21);
        order2.addPurchace(purchase22);
        saveOrUpdate(order2);

        user2.addOrder(order2);
        user1.addOrder(order2);


        Order order3 = new Order(LocalDateTime.of(2025, 1, 5, 18, 1), user3);
        
        
        Purchase purchase31 = new Purchase(user3, product4, order3, 1);
        saveOrUpdate(purchase31);
        
        order3.addPurchace(purchase31);



        user3.addOrder(order3);

        saveOrUpdate(order3);

    }


    public static RepositoryMock getInstance() {
        return singleton;
    }

}
