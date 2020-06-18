package com.miller.osWorks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miller.osWorks.domain.model.Client;
import com.miller.osWorks.domain.model.ServiceOrder;
import com.miller.osWorks.domain.model.StatusServiceOrder;
import com.miller.osWorks.domain.repository.ClientRepository;
import com.miller.osWorks.domain.repository.ServiceOrderRepository;
import com.miller.osWorks.domain.exception.BussinessException;

@Service
public class CrudServiceOrderService {

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	public ServiceOrder create(ServiceOrder serviceOrder) {
		
		Client client = clientRepository.findById(serviceOrder.getClient().getId())
				.orElseThrow(() -> new BussinessException("Cliente não encontrado"));
		
		serviceOrder.setClient(client);
		serviceOrder.setStatus(StatusServiceOrder.OPENED);
		serviceOrder.setOpenDate(OffsetDateTime.now());
		
		return serviceOrderRepository.save(serviceOrder);
	}
}
