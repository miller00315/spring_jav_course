package com.miller.osWorks.api.controller;

import java.util.List;
import java.util.Optional;

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
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrder create(@Valid @RequestBody ServiceOrder serviceOrder) {
		return crudServiceOrder.create(serviceOrder);
	}
	
	@GetMapping
	public List<ServiceOrder> list() {
		return serviceOrderRepository.findAll();
	}
	
	@GetMapping("/{serviceOrderId}")
	public ResponseEntity<ServiceOrder> search(@PathVariable Long serviceOrderId) {
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(!serviceOrder.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(serviceOrder.get());
	}
}
