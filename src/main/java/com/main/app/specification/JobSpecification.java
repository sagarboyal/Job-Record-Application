package com.main.app.specification;

import com.main.app.entity.Job;
import com.main.app.entity.JobStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class JobSpecification {

    public static Specification<Job> hasStatus(JobStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Job> hasCompany(String company) {
        return (root, query, cb) -> company == null ? null : cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%");
    }

    public static Specification<Job> hasRole(String roleName) {
        return (root, query, cb) -> roleName == null ? null : cb.equal(root.join("role").get("name"), roleName);
    }

    public static Specification<Job> appliedBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) return cb.between(root.get("appliedAt"), start.atStartOfDay(), end.atTime(23, 59, 59));
            if (start != null) return cb.greaterThanOrEqualTo(root.get("appliedAt"), start.atStartOfDay());
            return cb.lessThanOrEqualTo(root.get("appliedAt"), end.atTime(23, 59, 59));
        };
    }
}
