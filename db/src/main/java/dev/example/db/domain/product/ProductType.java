package dev.example.db.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 상품 타입
@RequiredArgsConstructor
@Getter
public enum ProductType {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;

}
