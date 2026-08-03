package com.ecommerce.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    @NotBlank
    @Size(min = 3, message = "minimum 3 characters required.")
    private String productName;
    private String image;

    @NotBlank
    @Size(min = 5, message = "minimum 5 characters required.")
    private String description;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double price;

    @NotNull
    private Double discount;
    private Double specialPrice;
}
