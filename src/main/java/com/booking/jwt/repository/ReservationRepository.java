package com.booking.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.booking.jwt.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>,JpaSpecificationExecutor<Reservation> {
	
}
