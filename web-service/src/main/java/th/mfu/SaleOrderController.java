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

import th.mfu.dto.SaleOrderDto;
import th.mfu.mapper.SaleOrderMapper;

@RestController
@RequestMapping("/orders")
public class SaleOrderController {

    @Autowired
    private SaleOrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @GetMapping
    public ResponseEntity<Iterable<SaleOrderDto>> getAllOrders() {
        Iterable<SaleOrder> orders = orderRepo.findAll();
        // Convert to DTOs - we'll need to handle this differently since it's Iterable
        return new ResponseEntity<>(saleOrderMapper.toDtoList((java.util.List<SaleOrder>) orders), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleOrderDto> getOrderById(@PathVariable Long id) {
        Optional<SaleOrder> order = orderRepo.findById(id);
        if (order.isPresent()) {
            SaleOrderDto orderDto = saleOrderMapper.toDto(order.get());
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SaleOrderDto> createOrder(@RequestBody SaleOrderDto orderDto) {
        if (orderDto.getCustomer() == null || orderDto.getCustomer().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> customerOpt = customerRepo.findById(orderDto.getCustomer().getId());
        if (!customerOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        SaleOrder newOrder = new SaleOrder();
        newOrder.setCustomer(customerOpt.get());
        newOrder.setOrderDate(LocalDate.now());

        if (orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        double totalAmount = 0.0;

        for (th.mfu.dto.SaleOrderItemDto itemDto : orderDto.getItems()) {
            if (itemDto.getProduct() == null || itemDto.getProduct().getId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Product> productOpt = productRepo.findById(itemDto.getProduct().getId());
            if (!productOpt.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Product product = productOpt.get();
            
            SaleOrderItem item = new SaleOrderItem();
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(itemDto.getQuantity());
            item.setSaleOrder(newOrder);
            newOrder.getItems().add(item);
            
            totalAmount += item.getPrice() * item.getQuantity();
        }

        newOrder.setTotalAmount(totalAmount);

        SaleOrder savedOrder = orderRepo.save(newOrder);
        SaleOrderDto savedOrderDto = saleOrderMapper.toDto(savedOrder);
        return new ResponseEntity<>(savedOrderDto, HttpStatus.CREATED);
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


