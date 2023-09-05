package com.de.projectbackend.controller;

import com.de.projectbackend.model.Product;
import com.de.projectbackend.services.ProductService;
import com.de.projectbackend.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService){this.productService = productService;}

    @PostMapping("/products")
    public Product createProduct(@RequestBody @Valid Product product){

        return productService.createProductSafe(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProductsSafe();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable Integer id){
        boolean deleted = false;
        deleted = productService.deleteProductSafe(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        Product product = null;
        product = productService.getProductByIdSafe(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product){
        product = productService.updateProductSafe(id,product);
        return ResponseEntity.ok(product);
    }
}
