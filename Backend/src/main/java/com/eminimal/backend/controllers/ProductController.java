package com.eminimal.backend.controllers;

import com.eminimal.backend.dto.ProductDto;
import com.eminimal.backend.models.Product;
import com.eminimal.backend.services.impl.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    //   Get information
    @GetMapping("/all")
    List<ProductDto> findAll(){
        return service.findAll().stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/search/id={id}")
    ResponseEntity<ProductDto> findProductById(@PathVariable UUID id){
        Product product = service.findById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return  ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/search/name={name}")
    ResponseEntity<ProductDto> findProductByName(@PathVariable String name){
        Optional<Product> product =  service.findProductByProductName(name);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        logger.info("Find: " + productDto);
        return ResponseEntity.ok().body(productDto);
    }

    //  Create product
    @PostMapping("/create")
    ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        Product productRequest = modelMapper.map(productDto, Product.class);
        Product product = service.save(productRequest);


//        Entity -> DTO
        ProductDto productResponse = modelMapper.map(product, ProductDto.class);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    //  Update product
    @PutMapping("/update")
    ResponseEntity<ProductDto> updateProduct(@RequestParam UUID id, @RequestBody ProductDto productDto){
        Product productRequest = modelMapper.map(productDto, Product.class);
        Product product = service.updateProduct(id, productRequest);

//        Entity -> DTO
        ProductDto productResponse = modelMapper.map(product, ProductDto.class);

        return ResponseEntity.ok().body(productResponse);
    }

    //  Delete product
    @DeleteMapping("/delete")
    ResponseEntity<io.swagger.v3.oas.models.responses.ApiResponse> deleteProductById(@RequestParam UUID id){
        service.deleteById(id);
        io.swagger.v3.oas.models.responses.ApiResponse apiResponse = new io.swagger.v3.oas.models.responses.ApiResponse();
        apiResponse.setDescription("Remove successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
