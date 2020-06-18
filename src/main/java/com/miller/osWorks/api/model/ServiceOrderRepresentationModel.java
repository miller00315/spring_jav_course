package com.miller.osWorks.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.miller.osWorks.domain.model.StatusServiceOrder;

public class ServiceOrderRepresentationModel {
	
	private Long id;
	private ClientResumedModel client;
	private String description;
	private BigDecimal price;
	private StatusServiceOrder status;
	private OffsetDateTime open_date;
	private OffsetDateTime finish_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClientResumedModel getClient() {
		return client;
	}
	public void setClient(ClientResumedModel client) {
		this.client = client;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public StatusServiceOrder getStatus() {
		return status;
	}
	public void setStatus(StatusServiceOrder status) {
		this.status = status;
	}
	public OffsetDateTime getOpen_date() {
		return open_date;
	}
	public void setOpen_date(OffsetDateTime open_date) {
		this.open_date = open_date;
	}
	public OffsetDateTime getFinish_date() {
		return finish_date;
	}
	public void setFinish_date(OffsetDateTime finish_date) {
		this.finish_date = finish_date;
	}
}
