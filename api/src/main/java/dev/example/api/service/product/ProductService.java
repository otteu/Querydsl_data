package dev.example.api.service.product;

import dev.example.api.service.product.response.ProductResponse;
import dev.example.db.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Service 계층에서 Response 오브젝트 겍체를 만들어서 반환한다.
    // 조회 와 동시에 형 변환을 한다.
//    public List<ProductResponse> getSellingProduct() {
//
//    }

}
