package com.besafx.app.search;

import com.besafx.app.entity.Operation;
import com.besafx.app.entity.OperationType;
import com.besafx.app.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OperationSearch {

    @Autowired
    private OperationService operationService;

    public List<Operation> search(
            final Long codeFrom,
            final Long codeTo,
            final String title,
            final String deliveryManFrom,
            final String deliveryManTo,
            final Operation.DeliveryWay deliveryWay,
            final String deliveryAddress,
            final Operation.Importance importance,
            final Operation.Structure structure,
            final Boolean locked,
            final Long startDateFrom,
            final Long startDateTo,
            final Long endDateFrom,
            final Long endDateTo,
            final Long fromId,
            final Operation.IdType fromType,
            final Long toId,
            final Operation.IdType toType,
            final Long person,
            final Long operationType
    ) {

        List<Specification> predicates = new ArrayList<>();
        Optional.ofNullable(codeFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(codeTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("code"), value)));
        Optional.ofNullable(title).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("title"), "%" + value + "%")));
        Optional.ofNullable(deliveryManFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("deliveryManFrom"), "%" + value + "%")));
        Optional.ofNullable(deliveryManTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("deliveryManTo"), "%" + value + "%")));
        Optional.ofNullable(deliveryWay).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("deliveryWay"), value)));
        Optional.ofNullable(deliveryAddress).ifPresent(value -> predicates.add((root, cq, cb) -> cb.like(root.get("deliveryAddress"), "%" + value + "%")));
        Optional.ofNullable(importance).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("importance"), value)));
        Optional.ofNullable(structure).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("structure"), value)));
        Optional.ofNullable(locked).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("locked"), value)));
        Optional.ofNullable(startDateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), new Date(value))));
        Optional.ofNullable(startDateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("startDate"), new Date(value))));
        Optional.ofNullable(endDateFrom).ifPresent(value -> predicates.add((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("endDate"), new Date(value))));
        Optional.ofNullable(endDateTo).ifPresent(value -> predicates.add((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("endDate"), new Date(value))));
        Optional.ofNullable(fromId).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("fromId"), value)));
        Optional.ofNullable(fromType).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("fromType"), value)));
        Optional.ofNullable(toId).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("toId"), value)));
        Optional.ofNullable(toType).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("toType"), value)));
        Optional.ofNullable(person).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("person").get("id"), value)));
        Optional.ofNullable(operationType).ifPresent(value -> predicates.add((root, cq, cb) -> cb.equal(root.get("operationType").get("id"), value)));

        if (!predicates.isEmpty()) {
            Specification result = predicates.get(0);
            for (int i = 1; i < predicates.size(); i++) {
                result = Specifications.where(result).and(predicates.get(i));
            }
            return operationService.findAll(result);
        } else {
            return null;
        }

    }
}
