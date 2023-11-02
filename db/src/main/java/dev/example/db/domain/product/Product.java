package dev.example.db.domain.product;

import dev.example.db.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String productNumber;

    // 상품 종료
    @Enumerated(EnumType.STRING)
    private ProductType type;

    // 판매 상태
    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingType;

    private String name;

    private int price;

    @Builder
    private Product(String productNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingType = sellingType;
        this.name = name;
        this.price = price;
    }

    public static Product create(String productNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        return Product.builder()
                      .productNumber(productNumber)
                      .type(type)
                      .sellingType(sellingType)
                      .name(name)
                      .price(price)
                      .build();
    }
}
