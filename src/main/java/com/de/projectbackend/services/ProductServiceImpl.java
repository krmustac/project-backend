package com.de.projectbackend.services;

import com.de.projectbackend.entity.ProductEntity;
import com.de.projectbackend.model.Product;
import com.de.projectbackend.model.User;
import com.de.projectbackend.repository.ProductRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductServiceImpl(ProductRepository productRepository,
                              NamedParameterJdbcTemplate jdbcTemplate){
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @NotNull
    private RowMapper<Product> getProductRowMapper(){
        return ((rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setProductName(rs.getString("product_name"));
            product.setAmount(rs.getObject("amount", Integer.class));
            product.setSupplier(rs.getString("supplier"));
            product.setContact(rs.getString("contact"));
            return product;
        });
    }
    @Override
    public Product createProduct(Product product) throws Exception {
        try {
            ProductEntity productEntity = new ProductEntity();

            BeanUtils.copyProperties(product, productEntity);
            productRepository.save(productEntity);

            return product;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    public Product createProductPrepStatement(Product product) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("productName", product.getProductName());
            paramMap.put("amount", product.getAmount());
            paramMap.put("supplier", product.getSupplier());
            paramMap.put("contact", product.getContact());

            final String sql = """
                INSERT INTO products (
                product_name,
                amount,
                supplier,
                contact)
                VALUES (
                :productName,
                :amount,
                :supplier,
                :contact)
                """;
        boolean created = jdbcTemplate.update(sql,paramMap)>0;
        return created ? product : null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }


    public List<Product> getAllProductsPrepStatement() throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            final String sql = """
                SELECT * FROM products
                """;
        List<Product> products = jdbcTemplate.query(sql,paramMap,getProductRowMapper());

        return products;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        try {
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
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public boolean deleteProduct(Integer id) throws Exception {
        try {
            ProductEntity product = productRepository.findById(id).get();
            productRepository.delete(product);
            return true;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    public boolean deleteProductPrepStatement(Integer id) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);

            final String sql = """
                DELETE FROM products WHERE id = :id
                """;

        return jdbcTemplate.update(sql,paramMap) > 0;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public Product getProductById(Integer id) throws Exception {
        try {
            ProductEntity productEntity = productRepository.findById(id).get();
            Product product = new Product();
            BeanUtils.copyProperties(productEntity, product);

            return product;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    public Product getProductByIdPrepStatement(Integer id) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            final String sql = """
                SELECT * FROM products WHERE id = :id
                """;

        List<Product> products = jdbcTemplate.query(sql,paramMap,getProductRowMapper());
        return products.size() > 0 ? products.get(0) : null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    @Override
    public Product updateProduct(Integer id, Product product) throws Exception {
        try {
            ProductEntity productEntity = productRepository.findById(id).get();
            productEntity.setProductName(product.getProductName());
            productEntity.setAmount(product.getAmount());
            productEntity.setSupplier(product.getSupplier());
            productEntity.setContact(product.getContact());
            productRepository.save(productEntity);

            return product;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    public Product updateProductPrepStatement(Integer id, Product product) throws Exception {
        try {
            final HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            paramMap.put("productName", product.getProductName());
            paramMap.put("amount", product.getAmount());
            paramMap.put("supplier", product.getSupplier());
            paramMap.put("contact", product.getContact());

            final String sql = """
                UPDATE products
                SET
                product_name = :productName,
                amount = :amount,
                supplier = :supplier,
                contact = :contact
                WHERE id = :id
                """;

        boolean updated = jdbcTemplate.update(sql,paramMap) > 0;

        return updated ? product : null;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }

    public Integer getExampleProcedureCall(String productName) throws Exception {
        try {
            Integer count = productRepository.GET_TOTAL_PRODUCTS_BY_NAME(productName);
            return count;
        }catch (Exception exception){
            throw new Exception("Something went wrong ensure all data is correct");
        }
    }
}
