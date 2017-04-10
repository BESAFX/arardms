package com.besafx.app.service;

import com.besafx.app.entity.Operation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface OperationService extends PagingAndSortingRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("select max(code) from Operation o where (o.structure) = (:structure) and (o.fromType) = (:fromType) and (o.fromId) = (:fromId)")
    Integer findMaxCodeByStructureAndFromTypeAndFromId(@Param("structure") Operation.Structure structure, @Param("fromType") Operation.IdType fromType, @Param("fromId") Long fromId);

    @Query("select max(code) from Operation o where (o.structure) = (:structure) and (o.toType) = (:toType) and (o.toId) = (:toId)")
    Integer findMaxCodeByStructureAndToTypeAndToId(@Param("structure") Operation.Structure structure, @Param("toType") Operation.IdType toType, @Param("toId") Long toId);

}
