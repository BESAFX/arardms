package com.besafx.app.service;
import com.besafx.app.entity.Branch;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BranchService extends PagingAndSortingRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    Branch findByCode(String code);
    Branch findByName(String code);
    Branch findByCodeAndIdIsNot(String code, Long id);
}
