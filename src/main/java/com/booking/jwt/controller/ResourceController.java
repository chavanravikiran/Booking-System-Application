package com.booking.jwt.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.domain.Pageable;
import com.booking.jwt.dto.ResourceDTO;
import com.booking.jwt.entity.Resource;
import com.booking.jwt.service.ResourceService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/resources")
@CrossOrigin
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Page<Resource> list(@PageableDefault(page = 0, size = 10)@SortDefault(sort = "createdOn", direction = Sort.Direction.DESC) Pageable pageable) {
        return resourceService.listAll(pageable);
    }
 
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Optional<Resource> getById(@PathVariable Long id) {
        return resourceService.getById(id);
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceDTO> create(@RequestBody ResourceDTO dto) {
        Resource saved = resourceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResourceDTO.fromEntity(saved));
    }
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceDTO> update(@PathVariable Long id, @RequestBody ResourceDTO dto) {
        Resource updated = resourceService.update(id, dto);
        return ResponseEntity.ok(ResourceDTO.fromEntity(updated));
    }
	
	@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resourceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
