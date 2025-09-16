package com.booking.jwt.spec;

import org.springframework.data.jpa.domain.Specification;

import com.booking.jwt.entity.Reservation;
import com.booking.jwt.entity.ReservationStatus;

import java.math.BigDecimal;

public class ReservationSpecification {

    public static Specification<Reservation> hasStatus(ReservationStatus status) {
        return (root, cq, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Reservation> priceGreaterThanOrEq(BigDecimal min) {
        return (root, cq, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Reservation> priceLessThanOrEq(BigDecimal max) {
        return (root, cq, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<Reservation> ownedByUser(Long userId) {
        return (root, cq, cb) -> userId == null ? null : cb.equal(root.get("user").get("userId"), userId);
    }

    public static Specification<Reservation> resourceIdEquals(Long resourceId) {
        return (root, cq, cb) -> resourceId == null ? null : cb.equal(root.get("resource").get("resourceId"), resourceId);
    }
}