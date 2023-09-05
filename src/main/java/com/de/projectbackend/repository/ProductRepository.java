package com.de.projectbackend.repository;

import com.de.projectbackend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    @Procedure
    int GET_TOTAL_PRODUCTS_BY_NAME(String productName);
}
