package th.mfu.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import th.mfu.Customer;
import th.mfu.dto.CustomerDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    CustomerDTO toDto(Customer entity);

    @Mapping(target = "saleOrders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Customer toEntity(CustomerDTO dto);

    List<CustomerDTO> toDtoList(List<Customer> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleOrders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateEntityFromDto(CustomerDTO dto, @MappingTarget Customer customer);
}
