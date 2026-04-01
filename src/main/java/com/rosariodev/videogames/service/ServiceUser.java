package com.rosariodev.videogames.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosariodev.videogames.config.SecurityConfig;
import com.rosariodev.videogames.enums.ERole;
import com.rosariodev.videogames.model.Order;
import com.rosariodev.videogames.model.Product;
import com.rosariodev.videogames.model.Purchase;
import com.rosariodev.videogames.model.User;
import com.rosariodev.videogames.model.dto.OrderDTO;
import com.rosariodev.videogames.model.dto.PurchaseDTO;
import com.rosariodev.videogames.model.dto.UserLoginDTO;
import com.rosariodev.videogames.model.dto.UserRegisterDTO;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOOrder;
import com.rosariodev.videogames.persistence.IDAOProduct;
import com.rosariodev.videogames.persistence.IDAOPurchase;
import com.rosariodev.videogames.persistence.IDAOUser;
import com.rosariodev.videogames.persistence.hibernate.DAOUtilHibernate;
import com.rosariodev.videogames.util.JWTUtil;
import com.rosariodev.videogames.util.Mapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceUser {


    @Autowired 
    private IDAOUser daoUser;
    @Autowired 
    private IDAOPurchase daoPurchase;
    @Autowired 
    private IDAOProduct daoProduct;
    @Autowired
    private IDAOOrder daoOrder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String login(UserLoginDTO userDTO) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(userDTO.getEmail());
            DAOUtilHibernate.commitTransaction();
            if (user == null) throw new IllegalArgumentException("Email or password is incorrect!");
            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) throw new IllegalArgumentException("Email or password is incorrect!");
            return JWTUtil.generateToken(userDTO.getEmail());
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public void register(UserRegisterDTO userDTO) {
        try {
            DAOUtilHibernate.beginTransaction();
            User existedUser = daoUser.getUserByEmail(userDTO.getEmail());
            if (existedUser != null) throw new IllegalArgumentException("Account already exists!");
            User user = new User(userDTO.getName(), userDTO.getSurname(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), ERole.USER);
            daoUser.makePersistent(user);
            DAOUtilHibernate.commitTransaction();
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public List<UserLoginDTO> getAllUsers() {
        try {
            DAOUtilHibernate.beginTransaction();
            List<User> users = daoUser.findAll();
            DAOUtilHibernate.commitTransaction();
            return Mapper.map(users, UserLoginDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public Long createOrder(OrderDTO orderDTO, String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            if (orderDTO.getId() == null) throw new IllegalArgumentException("Order ID is null");
            User user = daoUser.getUserByEmail(email);
            Order order = new Order(LocalDateTime.now(), user);
            daoOrder.makePersistent(order);
            for (PurchaseDTO purchaseDTO : orderDTO.getListPurchase()) {
                if (purchaseDTO.getId() != null) throw new IllegalArgumentException("Purchase already exists");
                Product product = daoProduct.findById(purchaseDTO.getProduct().getId());
                if (product == null) throw new IllegalArgumentException("Product not found");
                Purchase purchase = new Purchase(user, product, order, purchaseDTO.getQuantity());
                user.addPurchase(purchase);
                daoPurchase.makePersistent(purchase);
            }
            user.getOrders().add(order);
            daoUser.makePersistent(user);
            DAOUtilHibernate.commitTransaction();
            return orderDTO.getId();
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public OrderDTO updateOrder(Long idOrder, OrderDTO updatedOrderDTO, String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            Order order = daoOrder.findById(idOrder);
            if (order == null) throw new IllegalArgumentException("Order with ID " + idOrder + " not found");
            if (!user.getOrders().contains(order)) throw new IllegalArgumentException("Order not found for this user");
            Map<Long, Integer> productQuantityMap = new HashMap<>();
            for (PurchaseDTO p : updatedOrderDTO.getListPurchase()) {
                productQuantityMap.put(p.getProduct().getId(), p.getQuantity());
            }
            order.setData(updatedOrderDTO.getData());
            for (Iterator<Purchase> it = order.getListPurchase().iterator(); it.hasNext();) {
                Purchase existing = it.next();
                Integer newQty = productQuantityMap.get(existing.getProduct().getId());
                if (newQty != null) {
                    productQuantityMap.remove(existing.getProduct().getId());
                    if (!newQty.equals(existing.getQuantity())) {
                        existing.setQuantity(newQty);
                        daoPurchase.makePersistent(existing);
                    }
                } else {
                    it.remove();
                    daoPurchase.makeTransient(existing);
                }
            }
            for (Long idProduct : productQuantityMap.keySet()) {
                Product product = daoProduct.findById(idProduct);
                if (product == null) throw new IllegalArgumentException("Product not found");
                Purchase newPurchase = new Purchase(user, product, order, productQuantityMap.get(idProduct));
                order.getListPurchase().add(newPurchase);
                daoPurchase.makePersistent(newPurchase);
            }
            order = daoOrder.findById(idOrder);
            DAOUtilHibernate.commitTransaction();
            return Mapper.map(order, OrderDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public List<OrderDTO> getOrders(String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            DAOUtilHibernate.commitTransaction();
            if (user.getOrders() == null) throw new IllegalArgumentException("No orders found!");
            for (Order order : user.getOrders()) order.setTotal(order.getTotalPrice());
            return Mapper.map(user.getOrders(), OrderDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public List<PurchaseDTO> getChart(String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            DAOUtilHibernate.commitTransaction();
            if (user.getPurchases() == null) throw new IllegalArgumentException("No purchases found!");
            return Mapper.map(user.getPurchases(), PurchaseDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public void removeProductFromChart(Long idProduct, String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            Product product = daoProduct.findById(idProduct);
            if (product == null) throw new IllegalArgumentException("Product with ID " + idProduct + " not found");
            User user = daoUser.getUserByEmail(email);
            List<Purchase> purchases = user.getPurchases();
            for (Purchase purchase : purchases) {
                if (purchase.getQuantity() > 0 && purchase.getProduct().getId().equals(idProduct)) {
                    purchase.setQuantity(purchase.getQuantity() - 1);
                    if (purchase.getQuantity() <= 0) {
                        user.getPurchases().remove(purchase);
                        daoPurchase.makeTransient(purchase);
                    }
                    break;
                } else {
                    user.getPurchases().remove(purchase);
                    daoPurchase.makeTransient(purchase);
                    break;
                }
            }
            DAOUtilHibernate.commitTransaction();
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public OrderDTO checkout(String email) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            List<Purchase> purchases = user.getPurchases();
            if (purchases == null || purchases.isEmpty()) throw new IllegalArgumentException("No purchases found!");
            Order order = new Order(LocalDateTime.now(), user);
            order.setListPurchase(purchases);
            order.setTotal(order.getTotalPrice());
            user.addOrder(order);
            OrderDTO orderDTO = Mapper.map(order, OrderDTO.class);
            orderDTO.setListPurchase(Mapper.map(purchases, PurchaseDTO.class));
            daoOrder.makePersistent(order);
            List<Purchase> emptyPurchases = user.getPurchases();
            emptyPurchases.clear();
            user.setPurchases(emptyPurchases);
            DAOUtilHibernate.commitTransaction();
            return orderDTO;
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }
}
    
