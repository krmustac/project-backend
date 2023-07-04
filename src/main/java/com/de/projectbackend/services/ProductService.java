package com.de.projectbackend.services;

import com.de.projectbackend.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    boolean deleteProduct(Integer id);

    Product getProductById(Integer id);

    Product updateProduct(Integer id, Product product);
}
