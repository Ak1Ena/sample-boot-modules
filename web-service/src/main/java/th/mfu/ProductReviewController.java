package th.mfu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.mfu.dto.ProductReviewDto;
import th.mfu.mapper.ProductReviewMapper;

@RestController
@RequestMapping("/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewRepository reviewRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private ProductReviewMapper productReviewMapper;

    @PostMapping
    public ResponseEntity<ProductReviewDto> createReview(@RequestBody ProductReviewDto reviewDto) {
        if (reviewDto.getCustomer() == null || reviewDto.getCustomer().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> customerOpt = customerRepo.findById(reviewDto.getCustomer().getId());
        if (!customerOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (reviewDto.getProduct() == null || reviewDto.getProduct().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Product> productOpt = productRepo.findById(reviewDto.getProduct().getId());
        if (!productOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductReview review = new ProductReview();
        review.setCustomer(customerOpt.get());
        review.setProduct(productOpt.get());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setReviewDate(LocalDate.now());

        ProductReview savedReview = reviewRepo.save(review);
        ProductReviewDto savedReviewDto = productReviewMapper.toDto(savedReview);
        return new ResponseEntity<>(savedReviewDto, HttpStatus.CREATED);
    }
}


