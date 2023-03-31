package com.exercise.MVC.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("")
    public void saveProduct(@RequestBody Product product){
        productRepository.save(product);
        System.out.println(productRepository.findByName("Playstation5"));
    }

    @GetMapping("/{name}")
    public Product readProduct(@PathVariable String name){
        return productRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nix Hier!"));
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody Product updateProduct){
       Product product = productRepository.findById(productId).orElseThrow(
               () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nicht da")
       );
       product.setName(updateProduct.getName());
       product.setCostEuro(updateProduct.getCostEuro());
       productRepository.save(product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId){
        productRepository.deleteById(productId);
    }
}
