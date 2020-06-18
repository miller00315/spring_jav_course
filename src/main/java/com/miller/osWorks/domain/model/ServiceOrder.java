package com.miller.osWorks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.miller.osWorks.domain.ValidationGroups;
import com.miller.osWorks.domain.exception.BussinessException;

@Entity
public class ServiceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClientId.class)
	@NotNull
	@ManyToOne
	private Client client;
	
	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty(access = Access.READ_ONLY)
	private StatusServiceOrder status;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime open_date;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime finish_date;
	
	@OneToMany(mappedBy = "service_order")
	private List<Comment> comments = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
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
	
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceOrder other = (ServiceOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean cantBeFinish() {
		return !StatusServiceOrder.OPENED.equals(this.getStatus());
	}

	public void finish() {
		if(this.cantBeFinish()) {
			throw new BussinessException("A ordem de serviço não pode ser finalizada");	
		}
		
		this.setStatus(StatusServiceOrder.FINISHED);
		this.setFinish_date(OffsetDateTime.now());
	}
}
