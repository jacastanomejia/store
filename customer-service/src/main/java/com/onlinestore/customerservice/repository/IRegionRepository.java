package com.onlinestore.customerservice.repository;


import com.onlinestore.customerservice.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegionRepository extends JpaRepository<Region, Long> {
}
