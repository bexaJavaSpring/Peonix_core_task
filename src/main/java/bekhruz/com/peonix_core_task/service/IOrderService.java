package bekhruz.com.peonix_core_task.service;

import bekhruz.com.peonix_core_task.dto.DataDto;
import bekhruz.com.peonix_core_task.dto.OrderDto;
import bekhruz.com.peonix_core_task.dto.OrderRequest;
import bekhruz.com.peonix_core_task.dto.PaymentRequest;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    DataDto<UUID> create(OrderRequest request);

    DataDto<OrderDto> get(UUID id);

    DataDto<UUID> update(UUID id, OrderRequest request);

    DataDto<Boolean> delete(UUID id);

    DataDto<List<OrderDto>> all();

    DataDto<Boolean> payment(PaymentRequest request);
}
