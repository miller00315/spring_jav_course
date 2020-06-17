package com.miller.osWorks.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author miller
 */

@RestController
public class ClientController {
	
	/**
	 * 
	 * @return String
	 */
	
	@GetMapping("/clients")
	public String list() {
		return "test";
	}
}
