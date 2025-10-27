package com.blinkit.order.client;

import com.blinkit.order.dtos.ProductResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/products/{Id}")
    ProductResponseDTO getProductDetails(@PathVariable("Id") String id);
}
