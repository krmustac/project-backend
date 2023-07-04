package com.de.projectbackend.services;

import com.de.projectbackend.entity.ProductEntity;
import com.de.projectbackend.model.Product;
import com.de.projectbackend.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){this.productRepository = productRepository;}

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();

        BeanUtils.copyProperties(product,productEntity);
        productRepository.save(productEntity);

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();

        List<Product> products = productEntities.stream()
                .map(prod -> new Product(
                        prod.getId(),
                        prod.getProductName(),
                        prod.getAmount(),
                        prod.getSupplier(),
                        prod.getContact()))
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        ProductEntity product = productRepository.findById(id).get();
        productRepository.delete(product);
        return true;
    }

    @Override
    public Product getProductById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        Product product = new Product();
        BeanUtils.copyProperties(productEntity,product);

        return product;
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        ProductEntity productEntity = productRepository.findById(id).get();
        productEntity.setProductName(product.getProductName());
        productEntity.setAmount(product.getAmount());
        productEntity.setSupplier(product.getSupplier());
        productEntity.setContact(product.getContact());
        productRepository.save(productEntity);

        return product;
    }
}
