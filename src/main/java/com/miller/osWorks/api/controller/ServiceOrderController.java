package com.miller.osWorks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.modelmapper.ModelMapper;

import com.miller.osWorks.api.model.ServiceOrderInput;
import com.miller.osWorks.api.model.ServiceOrderRepresentationModel;
import com.miller.osWorks.domain.model.ServiceOrder;
import com.miller.osWorks.domain.repository.ServiceOrderRepository;
import com.miller.osWorks.domain.service.CrudServiceOrderService;

@RestController
@RequestMapping("/service_orders")
public class ServiceOrderController {
	
	@Autowired
	private CrudServiceOrderService crudServiceOrder;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderRepresentationModel create(@Valid @RequestBody ServiceOrderInput serviceOrderInput) {
		
		ServiceOrder serviceOrder = toEntity(serviceOrderInput);
		
		return toRepresentationModel(crudServiceOrder.create(serviceOrder));
	}
	
	@GetMapping
	public List<ServiceOrderRepresentationModel> list() {
		return toServiceOrderRepresentationModel(serviceOrderRepository.findAll());
	}
	
	@GetMapping("/{serviceOrderId}")
	public ResponseEntity<ServiceOrderRepresentationModel> search(@PathVariable Long serviceOrderId) {
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(!serviceOrder.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(toRepresentationModel(serviceOrder.get()));
	}
	
	@PutMapping("/{service_order_id}/finish")
	public ResponseEntity<Void> finish(@PathVariable Long service_order_id) {
		
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(service_order_id);
		
		if(!serviceOrder.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		crudServiceOrder.finish(service_order_id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{service_order_id}")
	public ResponseEntity<Void> delete(@PathVariable Long service_order_id) {
		if(!serviceOrderRepository.existsById(service_order_id)) {
			return ResponseEntity.notFound().build();
		}
		
		crudServiceOrder.delete(service_order_id);
		
		return ResponseEntity.noContent().build();
	}
	
	private ServiceOrder toEntity(ServiceOrderInput serviceOrderInput) {
		return  modelMapper.map(serviceOrderInput, ServiceOrder.class);
	}
	
	private ServiceOrderRepresentationModel toRepresentationModel(ServiceOrder serviceOrder) {
		return  modelMapper.map(serviceOrder, ServiceOrderRepresentationModel.class);
	}
	
	private List<ServiceOrderRepresentationModel> toServiceOrderRepresentationModel(List<ServiceOrder> serviceOrders){
		return serviceOrders.stream()
				.map(serviceOrder -> toRepresentationModel(serviceOrder))
				.collect(Collectors.toList());
	}
}
