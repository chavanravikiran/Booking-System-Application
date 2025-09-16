package com.booking.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.booking.jwt.dto.CreateReservationRequest;
import com.booking.jwt.dto.ReservationDTO;
import com.booking.jwt.entity.Reservation;
import com.booking.jwt.entity.ReservationStatus;
import com.booking.jwt.entity.Resource;
import com.booking.jwt.entity.User;
import com.booking.jwt.repository.ReservationRepository;
import com.booking.jwt.repository.ResourceRepository;
import com.booking.jwt.repository.UserRepository;
import com.booking.jwt.spec.ReservationSpecification;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ReservationDTO create(CreateReservationRequest req, Principal principal) {
        Long userId = getUserIdByUsername(principal.getName());
        Reservation r = createReservation(userId, req.getResourceId(), req.getPrice(), req.getStartTime(), req.getEndTime(), true);
        return ReservationDTO.toDto(r);
    }
    
    private Reservation createReservation(Long userId, Long resourceId, BigDecimal price, Instant startTime,
    		Instant endTime, boolean flag) {
    	Optional<Resource> resource = resourceRepository.findById(resourceId);
    	if(resource.isPresent()) {
    		
    		if (startTime == null || endTime == null || !endTime.isAfter(startTime)) {
    			throw new IllegalArgumentException("Invalid start/end time");
    		}
    		if (flag) {
    			boolean overlap = reservationRepository.exists(
    					Specification.where(ReservationSpecification.resourceIdEquals(resourceId))
    					.and((root, cq, cb) -> cb.equal(root.get("status"), ReservationStatus.CONFIRMED))
    					.and((root, cq, cb) -> cb.or(
    							cb.and(cb.lessThan(root.get("startTime"), endTime), cb.greaterThan(root.get("endTime"), startTime)),
    							cb.and(cb.lessThanOrEqualTo(root.get("startTime"), startTime), cb.greaterThan(root.get("endTime"), startTime)),
    							cb.and(cb.lessThan(root.get("startTime"), endTime), cb.greaterThanOrEqualTo(root.get("endTime"), endTime))
    							))
    					);
    			if (overlap) {
    				throw new IllegalStateException("Resource already has a confirmed overlapping reservation in that time range");
    			}
    		}
    		
    		User user = userRepository.findById(userId)
    				.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    		
    		Reservation r = Reservation.builder()
    				.resource(resource.get())
    				.user(user)
    				.status(ReservationStatus.PENDING)
    				.price(price)
    				.startTime(startTime)
    				.endTime(endTime)
    				.build();
    		
    		return reservationRepository.save(r);
    	}
    	return null;
	}

	public ResponseEntity<ReservationDTO> getByIdWithAccessCheck(Long id, Principal principal) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long currentUserId = getUserIdByUsername(principal.getName());
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !r.getUser().getUserId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(ReservationDTO.toDto(r));
    }
    
    public Optional<Reservation> getById(Long id) {
        return reservationRepository.findById(id);
    }

    public Page<ReservationDTO> findSearchWiseFilteredList(Pageable pageable,ReservationStatus status,BigDecimal minPrice,
    		BigDecimal maxPrice,Long userId,boolean isAdmin) {
        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasStatus(status))
                .and(ReservationSpecification.priceGreaterThanOrEq(minPrice))
                .and(ReservationSpecification.priceLessThanOrEq(maxPrice));

        if (!isAdmin) {
            spec = spec.and(ReservationSpecification.ownedByUser(userId));
        }

        return reservationRepository.findAll(spec, pageable)
        		.map(ReservationDTO::fromEntity);
    }
    
    @Transactional
    public ReservationDTO update(Long id, CreateReservationRequest req, Principal principal) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long currentUserId = getUserIdByUsername(principal.getName());
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !existing.getUser().getUserId().equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        existing.setPrice(req.getPrice());
        existing.setStartTime(req.getStartTime());
        existing.setEndTime(req.getEndTime());

        Reservation updated = reservationRepository.save(existing);
        return ReservationDTO.toDto(updated);
    }

    @Transactional
    public void delete(Long id, Principal principal) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long currentUserId = getUserIdByUsername(principal.getName());
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !existing.getUser().getUserId().equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        reservationRepository.delete(existing);
    }
    
    public Long getUserIdByUsername(String username) {
        User u = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return u.getUserId();
    }
}