package th.mfu.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import th.mfu.Customer;
import th.mfu.dto.CustomerDto;

@Mapper(componentModel = "spring", uses = {SaleOrderMapper.class})
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    
    @Mapping(target = "saleOrders", ignore = true)
    CustomerDto toDto(Customer customer);
    
    @Mapping(target = "saleOrders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Customer toEntity(CustomerDto dto);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleOrders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer customer);
    
    List<CustomerDto> toDtoList(List<Customer> customers);
}
