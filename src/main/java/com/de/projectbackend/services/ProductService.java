package com.de.projectbackend.services;

import com.de.projectbackend.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product) throws Exception;

    List<Product> getAllProducts() throws Exception;

    boolean deleteProduct(Integer id) throws Exception;

    Product getProductById(Integer id) throws Exception;

    Product updateProduct(Integer id, Product product) throws Exception;
}
