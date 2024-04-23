package com.bitsu.productservice.service;

import com.bitsu.productservice.dto.ProductRequest;
import com.bitsu.productservice.dto.ProductResponse;
import com.bitsu.productservice.model.Product;
import com.bitsu.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public void createProdcut(ProductRequest productRequest){
        Product product = Product.builder()
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .name(productRequest.getName())
                .build();

        productRepository.save(product);
        log.info("Product Saved");
        log.info(product.toString());
    }


    public List<ProductResponse> getProduct(){
        return productRepository.findAll().stream()
                .map(this::mapToProductResponse
                ).toList();
    }

    public ProductResponse getProduct(String id){
        return mapToProductResponse(getProdcutById(id));

    }

    public void deleteProduct(String id){
        Product product = getProdcutById(id);
        productRepository.delete(product);

        log.info("Product Deleted");
        log.info(product.toString());
    }

    public void updateProduct(String id, ProductRequest productRequest){
        Product product = getProdcutById(id);
        product.setDescription(productRequest.getDescription());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        productRepository.save(product);
        log.info("Product Updated");
        log.info(product.toString());
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private Product getProdcutById(String id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) throw new NoSuchElementException(String.format("Can't find ID: %s", id));

        return product.get();
    }
}
