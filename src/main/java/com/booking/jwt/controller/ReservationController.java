package com.booking.jwt.controller;

import java.math.BigDecimal;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.booking.jwt.dto.CreateReservationRequest;
import com.booking.jwt.dto.ReservationDTO;
import com.booking.jwt.entity.ReservationStatus;
import com.booking.jwt.service.ReservationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@CrossOrigin
@RequiredArgsConstructor
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReservationDTO> list(
            Principal principal,@PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
        		.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        String username = principal.getName();
        Long userId = reservationService.getUserIdByUsername(username);

        return reservationService.findSearchWiseFilteredList(pageable,status,minPrice,maxPrice,userId,isAdmin);
    }
	
	@GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ReservationDTO> getById(@PathVariable Long id, Principal principal) {
        return reservationService.getByIdWithAccessCheck(id, principal);
    }

	@PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ReservationDTO create(@RequestBody CreateReservationRequest req, Principal principal) {
        return reservationService.create(req, principal);
    }
	
    //Done
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @RequestBody CreateReservationRequest req, Principal principal) {
        return ResponseEntity.ok(reservationService.update(id, req, principal));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        reservationService.delete(id, principal);
        return ResponseEntity.noContent().build();
    }
}
