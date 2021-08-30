package com.bank.domain.db.operators.internal;

import com.bank.domain.db.operators.Operator;
import com.bank.policy.users.rights.Right;

import java.util.Objects;
import java.util.Set;

public class Administrator extends Operator {
    private Integer id;
    private String workInfo;

    public Administrator(Integer id, String name,
                         String lastName, Set<Right> rights,
                         Integer id1, String workInfo) {
        super(id, name, lastName, rights);
        this.id = id1;
        this.workInfo = workInfo;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(String workInfo) {
        this.workInfo = workInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(id, that.id) && Objects.equals(workInfo, that.workInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workInfo);
    }
}
