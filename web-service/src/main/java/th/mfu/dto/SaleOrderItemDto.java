package th.mfu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrderItemDto {
    private Long id;
    @JsonIgnore
    private SaleOrderDto saleOrder;
    private ProductDto product;
    private Integer quantity;
    private Double price;
}
