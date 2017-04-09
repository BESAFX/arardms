package com.besafx.app.service;

import com.besafx.app.entity.Operation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface OperationService extends PagingAndSortingRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("select max(code) from Operation")
    Integer findMaxCode();

}
