package com.miller.osWorks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miller.osWorks.domain.model.Client;

/**
 * @author miller
 */

@RestController
public class ClientController {
	
	// Returns all the clients
	@GetMapping("/clients")
	public List<Client> list() {
		Client client1 = new Client();
		Client client2 = new Client();
		
		client1.setId(1L);
		client1.setName("Miller Oliveira");
		client1.setEmail("miller00315@gmail.com");
		client1.setPhone("23456789");
		
		client2.setId(2L);
		client2.setName("Carlos");
		client2.setEmail("carlos@gmail.com");
		client2.setPhone("90876543");
		
		return Arrays.asList(client1, client2);
	}
}
