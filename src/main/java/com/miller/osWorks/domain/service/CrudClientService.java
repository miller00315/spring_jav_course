package com.miller.osWorks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miller.osWorks.domain.exception.BussinessException;
import com.miller.osWorks.domain.model.Client;
import com.miller.osWorks.domain.repository.ClientRepository;

@Service
public class CrudClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		
		Client clientExist = clientRepository.findByEmail(client.getEmail());
		
		if(clientExist != null && !clientExist.equals(client)) {
			throw new BussinessException("Este cliente já existe");
		}
		
		return clientRepository.save(client);
	}
	
	public void delete(Long clientId) {
		clientRepository.deleteById(clientId);
	}

}
