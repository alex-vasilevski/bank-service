package com.bank.domain.db;


import com.bank.domain.db.operators.Operator;
import com.bank.policy.operation.OperationStatus;
import com.bank.policy.users.rights.Right;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class OperationMeta {

    private Integer id;
    private String operationName;
    private LocalDate operationStartTime;
    private LocalDate operationEndTime;
    private AccountEntity targetAccountEntity;
    private AccountEntity sourceAccountEntity;
    private Set<Right> usedRights;
    private Operator operator;
    private OperationStatus operationStatus;

    public OperationMeta(Integer id, String operationName, LocalDate operationStartTime,
                         LocalDate operationEndTime, AccountEntity targetAccountEntity,
                         AccountEntity sourceAccountEntity, Set<Right> usedRights,
                         Operator operator, OperationStatus operationStatus) {
        this.id = id;
        this.operationName = operationName;
        this.operationStartTime = operationStartTime;
        this.operationEndTime = operationEndTime;
        this.targetAccountEntity = targetAccountEntity;
        this.sourceAccountEntity = sourceAccountEntity;
        this.usedRights = usedRights;
        this.operator = operator;
        this.operationStatus = operationStatus;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public LocalDate getOperationStartTime() {
        return operationStartTime;
    }

    public void setOperationStartTime(LocalDate operationStartTime) {
        this.operationStartTime = operationStartTime;
    }

    public LocalDate getOperationEndTime() {
        return operationEndTime;
    }

    public void setOperationEndTime(LocalDate operationEndTime) {
        this.operationEndTime = operationEndTime;
    }

    public AccountEntity getTargetAccount() {
        return targetAccountEntity;
    }

    public void setTargetAccount(AccountEntity targetAccountEntity) {
        this.targetAccountEntity = targetAccountEntity;
    }

    public AccountEntity getSourceAccount() {
        return sourceAccountEntity;
    }

    public void setSourceAccount(AccountEntity sourceAccountEntity) {
        this.sourceAccountEntity = sourceAccountEntity;
    }

    public Set<Right> getUsedRights() {
        return usedRights;
    }

    public void setUsedRights(Set<Right> usedRights) {
        this.usedRights = usedRights;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationMeta that = (OperationMeta) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operationName, that.operationName) &&
                Objects.equals(operationStartTime, that.operationStartTime) &&
                Objects.equals(operationEndTime, that.operationEndTime) &&
                Objects.equals(targetAccountEntity, that.targetAccountEntity) &&
                Objects.equals(sourceAccountEntity, that.sourceAccountEntity) &&
                Objects.equals(usedRights, that.usedRights) &&
                Objects.equals(operator, that.operator) &&
                operationStatus == that.operationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operationName, operationStartTime,
                operationEndTime, targetAccountEntity, sourceAccountEntity,
                usedRights, operator, operationStatus);
    }
}
