package com.besafx.app.service;

import com.besafx.app.entity.OperationComment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface OperationCommentService extends PagingAndSortingRepository<OperationComment, Long>, JpaSpecificationExecutor<OperationComment> {

    @Query("select max(code) from OperationComment")
    Integer findMaxCode();

}
