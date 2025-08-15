package th.mfu;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import th.mfu.dto.ProductDto;
import th.mfu.dto.ProductReviewDto;
import th.mfu.mapper.ProductMapper;
import th.mfu.mapper.ProductReviewMapper;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private ProductReviewRepository reviewRepo;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ProductReviewMapper productReviewMapper;

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id){
        return prodRepo.findById(id)
                .map(product -> {
                    ProductDto productDto = productMapper.toDto(product);
                    return new ResponseEntity<>(productDto, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/products")
    public ResponseEntity<Collection<ProductDto>> getAllProducts(){
        List<Product> products = prodRepo.findAll();
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/products/description/{infix}")
    public ResponseEntity<Collection<ProductDto>> searchByDescription(@PathVariable String infix){
        List<Product> products = prodRepo.findByDescriptionContaining(infix);
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/products/price")
    public ResponseEntity<Collection<ProductDto>> listByPrice(){
        List<Product> products = prodRepo.findByOrderByPrice();
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<List<ProductReviewDto>> getReviewsForProduct(@PathVariable Integer id) {
        if (!prodRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProductReview> reviews = reviewRepo.findByProductId(id);
        List<ProductReviewDto> reviewDtos = productReviewMapper.toDtoList(reviews);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        Product product = productMapper.toEntity(productDto);
        prodRepo.save(product);
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        prodRepo.deleteById(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.NO_CONTENT);
    }

}


