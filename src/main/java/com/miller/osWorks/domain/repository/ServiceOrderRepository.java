package com.miller.osWorks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miller.osWorks.domain.model.ServiceOrder;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long>{

}
