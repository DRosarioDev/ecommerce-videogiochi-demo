package com.rosariodev.videogames.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosariodev.videogames.model.Product;
import com.rosariodev.videogames.model.Review;
import com.rosariodev.videogames.model.User;
import com.rosariodev.videogames.model.dto.ProductDTO;
import com.rosariodev.videogames.model.dto.ReviewDTO;
import com.rosariodev.videogames.model.dto.ReviewOutputDTO;
import com.rosariodev.videogames.persistence.DAOException;
import com.rosariodev.videogames.persistence.IDAOProduct;
import com.rosariodev.videogames.persistence.IDAOReview;
import com.rosariodev.videogames.persistence.IDAOUser;
import com.rosariodev.videogames.persistence.hibernate.DAOUtilHibernate;
import com.rosariodev.videogames.util.Mapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceProduct {

    @Autowired
    private IDAOProduct daoProduct;
    @Autowired
    private IDAOUser daoUser;
    @Autowired
    private IDAOReview daoReview;

    public List<ProductDTO> getAllProducts() {
        try {
            DAOUtilHibernate.beginTransaction();
            List<Product> products = daoProduct.findAll();
            DAOUtilHibernate.commitTransaction();
            return Mapper.map(products, ProductDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public ProductDTO getProductByID(Long id) {
        try {
            DAOUtilHibernate.beginTransaction();
            Product product = daoProduct.findById(id);
            DAOUtilHibernate.commitTransaction();
            if (product == null) throw new IllegalArgumentException("Product with ID " + id + " not found");
            return Mapper.map(product, ProductDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public List<ProductDTO> getProductsByName(String name) {
        try {
            DAOUtilHibernate.beginTransaction();
            List<Product> products = daoProduct.findByName(name);
            DAOUtilHibernate.commitTransaction();
            if (products.isEmpty()) throw new IllegalArgumentException("No products found with name: " + name);
            return Mapper.map(products, ProductDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public void writeReview(String email, Long idProduct, ReviewDTO review) {
        try {
            DAOUtilHibernate.beginTransaction();
            User user = daoUser.getUserByEmail(email);
            Product product = daoProduct.findById(idProduct);
            if (product == null) throw new IllegalArgumentException("Product with ID " + idProduct + " not found");
            if (!user.hasPurchasedProduct(product)) throw new IllegalArgumentException("You can only review products you have purchased");
            Review newReview = new Review(user, product, review.getReview());
            product.addReview(newReview);
            user.addReview(newReview);
            daoReview.makePersistent(newReview);
            DAOUtilHibernate.commitTransaction();
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }

    public List<ReviewOutputDTO> getReviewsByPdoductID(Long idProduct) {
        try {
            DAOUtilHibernate.beginTransaction();
            Product product = daoProduct.findById(idProduct);
            DAOUtilHibernate.commitTransaction();
            if (product == null) throw new IllegalArgumentException("Product with ID " + idProduct + " not found");
            List<Review> reviews = product.getReviews();
            if (reviews.isEmpty()) throw new IllegalArgumentException("No reviews found for product with ID: " + idProduct);
            return Mapper.map(reviews, ReviewOutputDTO.class);
        } catch (DAOException ex) {
            DAOUtilHibernate.rollbackTransaction();
            throw ex;
        }
    }
}