package com.javatechie.redis.controller;

import com.javatechie.redis.entity.Product;
import com.javatechie.redis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class RadisController {
    @Autowired
    private ProductDao dao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return dao.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return dao.findProductById(id);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id)   {
        return dao.deleteProduct(id);
    }
}
