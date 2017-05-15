package com.besafx.app.service;

import com.besafx.app.entity.Branch;
import com.besafx.app.entity.InsideOperation;
import com.besafx.app.entity.OperationType;
import com.besafx.app.entity.enums.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface InsideOperationService extends PagingAndSortingRepository<InsideOperation, Long>, JpaSpecificationExecutor<InsideOperation> {
    InsideOperation findTopByDirectionAndOperationTypeAndBranchFromAndBranchToOrderByCodeDesc(Direction direction, OperationType operationType, Branch branchFrom, Branch branchTo);
}
