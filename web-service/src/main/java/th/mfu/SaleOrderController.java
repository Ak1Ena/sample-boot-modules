package th.mfu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class SaleOrderController {

    @Autowired
    private SaleOrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public ResponseEntity<Iterable<SaleOrder>> getAllOrders() {
        return new ResponseEntity<>(orderRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleOrder> getOrderById(@PathVariable Long id) {
        Optional<SaleOrder> order = orderRepo.findById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SaleOrder> createOrder(@RequestBody SaleOrder newOrder) {
        if (newOrder.getCustomer() == null || newOrder.getCustomer().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> customerOpt = customerRepo.findById(newOrder.getCustomer().getId());
        if (!customerOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newOrder.setCustomer(customerOpt.get());

        newOrder.setOrderDate(LocalDate.now());

        double totalAmount = 0.0;

        if (newOrder.getItems() == null || newOrder.getItems().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (SaleOrderItem item : newOrder.getItems()) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Product> productOpt = productRepo.findById(item.getProduct().getId());
            if (!productOpt.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Product product = productOpt.get();
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setSaleOrder(newOrder);
            totalAmount += item.getPrice() * item.getQuantity();
        }

        newOrder.setTotalAmount(totalAmount);

        SaleOrder savedOrder = orderRepo.save(newOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        if (!orderRepo.existsById(id)) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        orderRepo.deleteById(id);
        return new ResponseEntity<>("Order deleted", HttpStatus.NO_CONTENT);
    }
}


