package com.eminimal.backend.repository;

import com.eminimal.backend.models.product.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, String> {
    ProductDetails findByProductDetailID (String id);
}