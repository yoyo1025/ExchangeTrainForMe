package com.example.demo.repositories;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="train1")
public class Train1 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	
	private LocalTime departureTime;
	
	private LocalTime arrivalTime;
	
	private int duration;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id; 
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    public LocalTime getDepartureTime() {
	    return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		//.setDepartureTime(LocalTime.of(14,33));のように使うらしい。
	    this.departureTime = departureTime;
	}
	
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
