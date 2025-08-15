package th.mfu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import th.mfu.Product;
import th.mfu.dto.ProductDto;

@Mapper(componentModel = "spring", uses = {ProductReviewMapper.class})
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    @Mapping(target = "reviews", ignore = true)
    ProductDto toDto(Product product);
    
    @Mapping(target = "saleOrderItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toEntity(ProductDto dto);
    
    List<ProductDto> toDtoList(List<Product> products);
}
