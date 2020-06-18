package com.miller.osWorks.api.model;

import java.time.OffsetDateTime;

public class CommentRepresentationModel {
	private Long id;
	private String description;
	private OffsetDateTime send_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OffsetDateTime getSend_date() {
		return send_date;
	}
	public void setSend_date(OffsetDateTime send_date) {
		this.send_date = send_date;
	}
	
	
}
