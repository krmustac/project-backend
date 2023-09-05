package com.de.projectbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    @NotBlank(message = "Product must have a name")
    private String productName;
    @Min(value = 0,message = "Product amount must be 0 or higher")
    @NotNull(message = "Product amount must be 0 or higher")
    private Integer amount;
    @NotBlank(message = "Supplier name must be included")
    private String supplier;
    @NotBlank(message = "Supplier contact info must be included")
    private String contact;

}
