package dev.example.api.service.product.response;

import dev.example.db.domain.product.ProductSellingType;
import dev.example.db.domain.product.ProductType;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

// Product Entity 객체를 대부분을 가여 존다.
@Getter
public class ProductResponse {
    private Long Id;
    private String productNumber;
    // 상품 종료
    private ProductType type;
    // 판매 상태
    private ProductSellingType sellingType;
    private String name;
    private int price;
}
