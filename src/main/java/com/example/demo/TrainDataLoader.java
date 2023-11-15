package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.demo.repositories.Train1;


public class TrainDataLoader {
	 public List<Train1> loadTrainData(String filePath){
	        List<Train1> trains = new ArrayList<>();
	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	        
	        try {
	            Resource resource = new ClassPathResource(filePath);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
	            
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(", ");
	                if (parts.length >= 5) {
	                    Train1 train = new Train1();
	                    train.setDepartureTime(LocalTime.parse(parts[0], timeFormatter));
	                    train.setArrivalTime(LocalTime.parse(parts[1], timeFormatter));
	                    train.setDuration(Integer.parseInt(parts[2].replaceAll("分", "")));
	                    train.setName(parts[3] + " " + parts[4]);
	                    trains.add(train);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return trains;
	    }
	 
	public void displayTrains(List<Train1> trains) {
        for (Train1 train : trains) {
            System.out.println("電車名: " + train.getName());
            System.out.println("発車時間: " + train.getDepartureTime());
            System.out.println("到着時間: " + train.getArrivalTime());
            System.out.println("所要時間: " + train.getDuration() + "分");
            System.out.println("-------------------------");
        }
    }
	
}