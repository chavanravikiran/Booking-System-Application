package com.booking.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.booking.jwt.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long>{

}
