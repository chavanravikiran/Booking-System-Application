package com.booking.jwt.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.booking.jwt.dto.ResourceDTO;
import com.booking.jwt.entity.Resource;
import com.booking.jwt.repository.ResourceRepository;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;
    
    public Page<Resource> listAll(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    public Optional<Resource> getById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource create(ResourceDTO dto) {
    	Resource resource = new Resource();
    	dto.setName(resource.getName());
    	dto.setType(resource.getType());
    	dto.setDescription(resource.getDescription());
    	dto.setCapacity(resource.getCapacity());
    	dto.setActive(resource.getActive());
    	
    	return resourceRepository.save(resource);
    }

    public Resource update(Long id, ResourceDTO update) {
        Resource existing = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + id));
        existing.setName(update.getName());
        existing.setType(update.getType());
        existing.setDescription(update.getDescription());
        existing.setCapacity(update.getCapacity());
        existing.setActive(update.getActive());
        return resourceRepository.save(existing);
    }

    public void delete(Long id) {
        resourceRepository.deleteById(id);
    }
}