package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.TrainDataLoader;
import com.example.demo.repositories.Train1;

@Controller
public class ExchangeController {
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("home");
		
		//現在の時刻を取得し、フォーマットする
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);
		
		//モデルに現在時刻を追加
		mav.addObject("currentDateTime", formattedDateTime);
		
		//タイトルを追加
		mav.addObject("title1","鴻巣駅から東大宮駅");
		mav.addObject("title2","東大宮駅から鴻巣駅");
		return mav;
	}
	
	@RequestMapping(path="Konosu-HigashiOmiya", method = RequestMethod.POST)
	public ModelAndView resultGoing(ModelAndView mav) {
	    // 現在の時刻を取得
	    LocalTime now = LocalTime.now();
	    
	    // ファイルから電車のデータを読み込む
	    String filePath = "C:\\pleiades\\2023-06\\workspace\\ExchangeTrainForMe\\src\\main\\resources\\static\\txt\\Konosu-Omiya.txt";
	    List<Train1> trains = trainDataLoader.loadTrainData(filePath);

	    Train1 nextTrain = null;
	    for (Train1 train : trains) {
	        if (train.getDepartureTime().isAfter(now)) {
	            nextTrain = train;
	            break; // 最初に見つかった将来の発車時刻の電車を選ぶ
	        }
	    }

	    if (nextTrain != null) {
	        // 次の電車の情報を ModelAndView に追加
	        mav.addObject("Departure", nextTrain.getDepartureTime());
	        mav.addObject("Arrival", nextTrain.getArrivalTime());
	        mav.addObject("TrainName", nextTrain.getName());
	    }

	    mav.setViewName("Konosu-HigashiOmiya");
	    return mav;
	}

	@RequestMapping(path="HigashiOmiya-Konosu", method = RequestMethod.POST)
	public ModelAndView resultBacking(ModelAndView mav) {
		mav.setViewName("HigashiOmiya-Konosu");
		return mav;
	}
	// TrainDataLoaderクラスのインスタンスを追加
    private TrainDataLoader trainDataLoader = new TrainDataLoader();
	
}