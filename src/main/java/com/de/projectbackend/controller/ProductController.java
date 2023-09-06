package com.de.projectbackend.controller;

import com.de.projectbackend.model.Product;
import com.de.projectbackend.services.ProductService;
import com.de.projectbackend.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
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
    public Product createProduct(@RequestBody @Valid Product product) throws Exception {
        return productService.createProduct(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable @PositiveOrZero Integer id)
            throws Exception {
        boolean deleted = false;
        deleted = productService.deleteProduct(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @PositiveOrZero Integer id) throws Exception {
        Product product = null;
        product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable @PositiveOrZero Integer id,
                                                 @RequestBody @Valid Product product) throws Exception {
        product = productService.updateProduct(id,product);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/procedure")
    public Integer exampleProcedureCall(@RequestBody String productName) throws Exception {
        return productService.getExampleProcedureCall(productName);
    }
}
