package th.mfu.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import th.mfu.LocalDateDeserializer;
import th.mfu.LocalDateSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrderDto {
    private Long id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate orderDate;
    private CustomerDto customer;
    private List<SaleOrderItemDto> items;
    private Double totalAmount;
}
