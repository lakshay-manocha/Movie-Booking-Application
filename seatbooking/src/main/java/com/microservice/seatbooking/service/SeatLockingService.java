package com.microservice.seatbooking.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.Uuid;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.microservice.common.events.SeatBookingEvent;
import com.microservice.common.events.SeatLockedEvent;
import com.microservice.common.events.SeatUnlockedEvent;
import com.microservice.seatbooking.entity.Seat;
import com.microservice.seatbooking.entity.Seat.SeatStatus;
import com.microservice.seatbooking.kafka.producer.SeatBookingProducer;
import com.microservice.seatbooking.repository.SeatRepository;

import jakarta.transaction.Transactional;

@Service
public class SeatLockingService {

	private final SeatRepository seatrepository;
	
	private final RedissonClient redissonclient;
	
	private final SeatBookingProducer bookingproducer;

	
	public SeatLockingService(SeatRepository seatrepository, RedissonClient redisonclient, SeatBookingProducer bookingproducer) {
		this.seatrepository=seatrepository;
		this.redissonclient = redisonclient;
		this.bookingproducer = bookingproducer;
		
	}
	
	@Transactional
	public Seat lockSeat (Long seatId, String userId) {
		String key = "seat-lock" + seatId;
		
		RLock lock = redissonclient.getLock(key);
		
		try {
			boolean aquired =
					lock.tryLock(5,30, TimeUnit.SECONDS);
			if(!aquired) {
				throw new RuntimeException("Seat Aquired by another user");
			}
			Seat seat = seatrepository.findById(seatId)
					.orElseThrow(()->
					new RuntimeException("No seat found"));
			
			if(seat.getStatus() == SeatStatus.LOCKED
					&& seat.getLockedExpiresAt()!=null
					&& seat.getLockedExpiresAt().isAfter(LocalDateTime.now())
					&& !(userId.equals(seat.getLockedBy()))
					)
				throw new RuntimeException("Seat Locked By Another user");
			seat.setStatus(SeatStatus.LOCKED);
			
			seat.setLockedBy(userId);
			
			seat.setLockedExpiresAt(LocalDateTime.now().plusMinutes(5));
			seat =  seatrepository.save(seat);
			
			SeatLockedEvent event = SeatLockedEvent.builder()
					.showId(seat.getShowId())
					.screenId(seat.getScreenId())
					.bookingId(Uuid.randomUuid().toString())
					.seatNumber(seat.getSeatNumber())
					.userId(seat.getUserId())
					.price(seat.getPrice())
					.seatType(seat.getSeatType().name())
					.lockExpiresAt(LocalDateTime.now().plusMinutes(5))
					.build();
			bookingproducer.publishSeatLockedEvent(event);
			
			return seat;
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupted();
			
			throw new RuntimeException("Lock Interrupted");
		}
		finally {
			if(lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}
	@Transactional
	public void unlockSeat(Long seatId, String userId) {
		Seat seat = seatrepository.findById(seatId)
				.orElseThrow(()-> new RuntimeException("No such seat exists"));
	
	if(!userId.equals(seat.getLockedBy())) {
		throw new RuntimeException("Seat not booked by user");
	}
	seat.setStatus(SeatStatus.AVAILABLE);
	seat.setLockedBy(null);
	seat.setLockedExpiresAt(null);
	seat.setUpdatedAt(LocalDateTime.now());
	
	seatrepository.save(seat);
	
    SeatUnlockedEvent event =
            SeatUnlockedEvent.builder()

            .seatId(seat.getSeatId())

            .showId(seat.getShowId())

            .userId(userId)

            .reason("PAYMENT_FAILED")

            .unlockedAt(LocalDateTime.now())

            .build();



    bookingproducer.publishSeatUnlockedEvent(event);

	
	
	}
	
	@Transactional
	public void bookSeat(Long seatId, String userId) {
		Seat seat = seatrepository.findById(seatId)
				.orElseThrow(()-> new RuntimeException("No seat found"));
	if(!userId.equals(seat.getSeatId())) {
		throw new RuntimeException("Seat is not Booked by user");
	}
	if(seat.getLockedExpiresAt().isBefore(LocalDateTime.now())) {
		throw new RuntimeException("Lock Already Expired");
	}
	seat.setStatus(SeatStatus.BOOKED);
	
	seat.setLockedBy(null);
	seat.setLockedExpiresAt(null);
	seatrepository.save(seat);
	
	SeatBookingEvent event = SeatBookingEvent.builder()
			.bookingId(Uuid.randomUuid().toString())
			.showId(seat.getShowId)
			.seatId(seat.getSeatId())
			.userId(userId)
			.price(seat.getPrice())
			.seatType(seat.getSeatType().name())
			.bookingTime(LocalDateTime.now())
			.build();
	bookingproducer.publishSeatBookedEvent(event);
	}
}
