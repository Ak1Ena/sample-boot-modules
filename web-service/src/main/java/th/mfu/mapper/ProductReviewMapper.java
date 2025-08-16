package th.mfu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import th.mfu.ProductReview;
import th.mfu.dto.ProductReviewDTO;

@Mapper(componentModel = "spring", uses = { CustomerMapper.class, ProductMapper.class })
public interface ProductReviewMapper {
    ProductReviewMapper INSTANCE = Mappers.getMapper(ProductReviewMapper.class);

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "product", source = "product")
    ProductReviewDTO toDto(ProductReview productReview);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductReview toEntity(ProductReviewDTO dto);

    List<ProductReviewDTO> toDtoList(List<ProductReview> productReviews);
}
