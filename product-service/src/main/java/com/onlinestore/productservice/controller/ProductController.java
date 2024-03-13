package com.onlinestore.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinestore.productservice.controller.dto.ErrorMessage;
import com.onlinestore.productservice.controller.dto.ProductDto;
import com.onlinestore.productservice.entity.Category;
import com.onlinestore.productservice.entity.Product;
import com.onlinestore.productservice.service.IProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products = new ArrayList<>();

        if(categoryId == null){
            products = productService.listAllProducts();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        List<ProductDto> productDtoList = products.stream()
                .map(prod -> mapper.map(prod, ProductDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        if(product == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mapper.map(product, ProductDto.class));
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        product = productService.create(product);
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> messages = result.getFieldErrors()
                .stream()
                .map(error-> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(error.getField(), error.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());

        ErrorMessage message = ErrorMessage.builder()
                .code("001")
                .message(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = "";
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info(jsonMessage);
        return jsonMessage;
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody Product product){
        product = productService.update(product);
        return ResponseEntity.ok(mapper.map(product, ProductDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        if(id == null){
            ResponseEntity.badRequest();
        }

        boolean success = productService.delete(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("true");
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductDto> updateStock(@PathVariable Long id, @RequestParam(name="quantity") Double stock){
        if(id == null || stock == null){
            return ResponseEntity.badRequest().build();
        }

        Product updated = productService.updateStock(id, stock);
        if(updated == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapper.map(updated, ProductDto.class));
    }
}
