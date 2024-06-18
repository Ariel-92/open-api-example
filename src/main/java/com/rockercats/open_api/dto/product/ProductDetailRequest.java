package com.rockercats.open_api.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailRequest {
    private Long productId;
    private String productName;
    private String description;
    private String color;
    private String saleStatCd;
    private String size;
    private String normalPrice;
    private String productDesc;
}
