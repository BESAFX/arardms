package com.besafx.app.service;

import com.besafx.app.entity.OperationAttach;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface OperationAttachService extends PagingAndSortingRepository<OperationAttach, Long>, JpaSpecificationExecutor<OperationAttach> {


}
