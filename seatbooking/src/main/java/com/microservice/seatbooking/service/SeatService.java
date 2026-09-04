package com.microservice.seatbooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.common.events.ShowCreatedEvent;
import com.microservice.seatbooking.entity.Seat;
import com.microservice.seatbooking.repository.SeatRepository;

import jakarta.transaction.Transactional;

@Service
public class SeatService {
	
	
	private final SeatRepository seatrepository;
	
	public SeatService (SeatRepository seatrepository) {
		this.seatrepository=seatrepository;
	}

	
	public Seat addSeat(Seat seat) {
		return seatrepository.save(seat);
	}


	public List<Seat> getAllSeat() {
		return seatrepository.findAll();
	}
	
	public Seat findByShowId(Long id) {
		return seatrepository.findById(id)
				.orElseThrow(()-> new RuntimeException("No Such Seat Exist"));
	}
	
	@Transactional
	public void genrateSeatsForShow (ShowCreatedEvent event) {
		
		if(!seatrepository
				.findBySeatId(Long.parseLong(event.getShowId()))
				.isEmpty()) {
			System.out.println("Seat already exist for show "+ event.getShowId());
			return;
		}
		
		List <Seat> seats = new ArrayList<>();
		
		int totalSeats = event.getTotalSeat();
		
		Long showId = Long.parseLong(event.getShowId());
		
		Long screenId =Long.parseLong(event.getScreenId());
		
		int seatsPerRow=10;
		
		for(int i =0; i<=seatsPerRow; i++) {

            Seat seat = new Seat();

            seat.setShowId(showId);

            seat.setScreenId(screenId);

            seat.setSeatNumber("S" + i);

            seat.setRowNumber((i - 1) / seatsPerRow);

            seat.setColumnNumber((i - 1) % seatsPerRow);

            seat.setPrice(event.getBasePrice().intValue());

            seat.setSeatType(
            	    Seat.SeatType.valueOf(event.getScreenType().toUpperCase())
            	);

            seat.setStatus(Seat.SeatStatus.AVAILABLE);

            seat.setLockedBy(null);

            seat.setLockedExpiresAt(null);

            seat.setCreatedAt(LocalDateTime.now());

            seat.setUpdatedAt(LocalDateTime.now());

            seat.setActive(true);

            seats.add(seat);
		}
		seatrepository.saveAll(seats);
		
        System.out.println(
                "Generated "
                + seats.size()
                + " seats for show "
                + showId);
	}

	public List<Seat> getSeatByShowId(Long ShowId){
		 return seatrepository.findByShowId(ShowId);	
	}


	public List<Seat> findBySeatId(Long seatId) {
		 return seatrepository.findBySeatId(seatId);
	}


}
