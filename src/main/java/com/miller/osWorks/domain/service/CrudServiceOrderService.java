package com.miller.osWorks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miller.osWorks.domain.model.Client;
import com.miller.osWorks.domain.model.Comment;
import com.miller.osWorks.domain.model.ServiceOrder;
import com.miller.osWorks.domain.model.StatusServiceOrder;
import com.miller.osWorks.domain.repository.ClientRepository;
import com.miller.osWorks.domain.repository.CommentRepository;
import com.miller.osWorks.domain.repository.ServiceOrderRepository;
import com.miller.osWorks.domain.exception.BussinessException;
import com.miller.osWorks.domain.exception.NotFoundEntityException;

@Service
public class CrudServiceOrderService {

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public ServiceOrder create(ServiceOrder serviceOrder) {
		
		Client client = clientRepository.findById(serviceOrder.getClient().getId())
				.orElseThrow(() -> new BussinessException("Cliente não encontrado"));
		
		serviceOrder.setClient(client);
		serviceOrder.setStatus(StatusServiceOrder.OPENED);
		serviceOrder.setOpen_date(OffsetDateTime.now());
		
		return serviceOrderRepository.save(serviceOrder);
	}
	
	public void delete(Long clientId) {
		serviceOrderRepository.deleteById(clientId);
	}
	
	public Comment addComment(Long serviceOrderId, String description) {
		
		ServiceOrder serviceOrder = getServiceOrder(serviceOrderId);
			
		Comment comment = new Comment();
		
		comment.setSend_date(OffsetDateTime.now());
		comment.setDescription(description);
		comment.setService_order(serviceOrder);
		
		return commentRepository.save(comment);
	}
	
	public void finish(Long serviceOrderId) {
		ServiceOrder serviceOrder = getServiceOrder(serviceOrderId);
		
		serviceOrder.finish();
		
		serviceOrderRepository.save(serviceOrder);
	}
	
	private ServiceOrder getServiceOrder(Long serviceOrderId) {
		return serviceOrderRepository.findById(serviceOrderId)
				.orElseThrow(() -> new NotFoundEntityException(
						"Ordem de serviço não encontrada"));
	}

}
