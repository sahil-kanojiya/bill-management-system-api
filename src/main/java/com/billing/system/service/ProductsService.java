package com.billing.system.service;

import com.billing.system.model.dto.ProductStockRequestDto;
import com.billing.system.model.Product;
import com.billing.system.model.Stock;
import com.billing.system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductStockRequestDto dto) {
        Product product=Product.builder()
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .gstRate(dto.getGstRate())
                .build();
        Stock stock=Stock.builder()
                .product(product)
                .stockQuantity(dto.getStockQuantity())
                .thresholdLevel(dto.getThresholdLevel())
                .build();
        product.setStock(stock);
        return productRepository.save(product);
    }

}
