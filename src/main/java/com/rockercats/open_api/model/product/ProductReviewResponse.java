package com.rockercats.open_api.model.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewResponse {
    private Long reviewId;
    private Long productId;
    private String reviewText;
    private int reviewScore;
}
