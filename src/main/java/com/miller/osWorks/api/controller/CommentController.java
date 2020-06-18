package com.miller.osWorks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miller.osWorks.api.model.CommentInput;
import com.miller.osWorks.api.model.CommentRepresentationModel;
import com.miller.osWorks.domain.exception.NotFoundEntityException;
import com.miller.osWorks.domain.model.Comment;
import com.miller.osWorks.domain.model.ServiceOrder;
import com.miller.osWorks.domain.repository.ServiceOrderRepository;
import com.miller.osWorks.domain.service.CrudServiceOrderService;

@RestController
@RequestMapping("/service_orders/{service_order_id}/comments")
public class CommentController {
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private CrudServiceOrderService crudServiceOrder;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CommentRepresentationModel add(@PathVariable Long service_order_id, 
			@Valid @RequestBody CommentInput comment_input) {
		
		Comment comment = crudServiceOrder.addComment(service_order_id, 
				comment_input.getDescription());
		
		return toRepresentationModel(comment);
	}
	
	@GetMapping
	public List<CommentRepresentationModel> list(@PathVariable Long service_order_id) {
		ServiceOrder serviceOrder = serviceOrderRepository.findById(service_order_id)
				.orElseThrow(() -> new NotFoundEntityException("Ordem de serviço não encontrada."));
		
		return toCollectionCommentRepresentationModel(serviceOrder.getComments());
	}
	
	private CommentRepresentationModel toRepresentationModel(Comment comment) {
		return  modelMapper.map(comment, CommentRepresentationModel.class);
	}
	
	private List<CommentRepresentationModel> toCollectionCommentRepresentationModel(List<Comment> comments) {
		return comments.stream()
				.map(comment -> toRepresentationModel(comment))
				.collect(Collectors.toList());
	}
}
