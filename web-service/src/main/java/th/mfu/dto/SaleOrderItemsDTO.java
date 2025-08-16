package th.mfu.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.mfu.Product;
import th.mfu.SaleOrder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrderItemsDTO {

    private Long id;

    private SaleOrderDTO saleOrder;
    private ProductDTO product;
    private Integer quantity;
    private Double price;
}
