package com.microservice.seatbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.seatbooking.entity.Seat;



@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{

	List<Seat> findBySeatId(long long1);

	List<Seat> findByShowId(Long showId);
	
	String findByBookingId(String bookingId);

}
