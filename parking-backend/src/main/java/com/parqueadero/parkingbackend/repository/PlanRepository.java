package com.parqueadero.parkingbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parqueadero.parkingbackend.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    
}
