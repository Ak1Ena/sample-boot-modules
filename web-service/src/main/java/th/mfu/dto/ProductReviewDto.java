package th.mfu.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProductReviewDto {
    private Long id;
    private CustomerDto customer;
    private ProductDto product;
    private int rating;
    private String comment;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate reviewDate;
}
