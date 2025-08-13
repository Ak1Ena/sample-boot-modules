package th.mfu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "sale_order_item")
public class SaleOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("order-items")
    private SaleOrder saleOrder;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private Double price;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public SaleOrder getSaleOrder() {
        return saleOrder;
    }
    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}


