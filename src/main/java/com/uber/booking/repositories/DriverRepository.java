package com.uber.booking.repositories;

import com.uber.common.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

    List<Driver> findByIdIn(List<String> driverIds);
}
