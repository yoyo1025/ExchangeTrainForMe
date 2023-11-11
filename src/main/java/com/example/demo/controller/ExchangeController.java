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
	
	@RequestMapping("https://exchangetrainforme.onrender.com/")
	public ModelAndView index(ModelAndView mav) {
		//遷移するページを追加
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
	
	//鴻巣から東大宮(大宮経由)に行く時のルート検索を行う
	@RequestMapping(path="Konosu-HigashiOmiya", method = RequestMethod.POST)
	public ModelAndView resultGoing(ModelAndView mav) {
		
	    // 現在の時刻を取得
	    LocalTime now = LocalTime.now();
	    
	    // ファイルから電車のデータを読み込む(鴻巣駅から大宮駅に行く高崎線か湘南新宿ライン)
	    String filePath1 = "src\\main\\resources\\static\\txt\\Konosu-Omiya.txt";
	    List<Train1> trains1 = trainDataLoader.loadTrainData(filePath1);
	    
	    //現在時刻から次に鴻巣駅から発車する電車を定義する
	    Train1 nextTrainFromKonosu = null;
	    
	    //先ほど定義した電車がどの電車になるのかを検索する
	    for (Train1 train : trains1) {
	        if (train.getDepartureTime().isAfter(now)) {
	            nextTrainFromKonosu = train;
	            break;
	        }
	    }
	    
	    // 次の電車の情報を ModelAndView に追加
	    if (nextTrainFromKonosu != null) {
	        mav.addObject("Departure1", nextTrainFromKonosu.getDepartureTime());
	        mav.addObject("Arrival1", nextTrainFromKonosu.getArrivalTime());
	        mav.addObject("TrainName1", nextTrainFromKonosu.getName());
	    }
	    
	    //ファイルから電車のデータを読み込む(大宮駅から東大宮駅に行く宇都宮線か湘南新宿ライン)
	    String filePath2 = "src\\main\\resources\\static\\txt\\Omiya-HigashiOmiya.txt";
	    List<Train1> trains2 = trainDataLoader.loadTrainData(filePath2);
	    
	    //鴻巣駅から大宮駅に着いて次に乗り換える電車を定義する
	    Train1 nextTrainFromOmiyaToHigashiOmiya = null;
	    
	    //自分が鴻巣駅からの乗ってきた電車が大宮駅に着いて乗換出来そうな東大宮行きの電車を検索する
	    for(Train1 train : trains2) {
	    	if(train.getDepartureTime().isAfter(nextTrainFromKonosu.getArrivalTime())) {
	    		nextTrainFromOmiyaToHigashiOmiya = train;
	    		break;
	    	}
	    }
	    
	    //乗り換える電車の情報を ModelAndView に追加
	    if(nextTrainFromOmiyaToHigashiOmiya != null) {
	    	mav.addObject("Departure2", nextTrainFromOmiyaToHigashiOmiya.getDepartureTime());
	    	mav.addObject("Arrival2", nextTrainFromOmiyaToHigashiOmiya.getArrivalTime());
	    	mav.addObject("TrainName2", nextTrainFromOmiyaToHigashiOmiya.getName());
	    }
	    
	    //遷移するページを追加
	    mav.setViewName("Konosu-HigashiOmiya"); 
	    return mav;
	}
	
	//東大宮から鴻巣(大宮経由)へ帰る時のルート検索を行う
	@RequestMapping(path="HigashiOmiya-Konosu", method = RequestMethod.POST)
	public ModelAndView resultBacking(ModelAndView mav) {
		
		// 現在の時刻を取得
	    LocalTime now = LocalTime.now();
		
		//ファイルから電車のデータを読み込む(東大宮駅から大宮駅に行く宇都宮線か湘南新宿ライン)
		String filePath3 = "src\\main\\resources\\static\\txt\\HigashiOmiya-Omiya.txt";
		List<Train1> train3 = trainDataLoader.loadTrainData(filePath3);
		
		Train1 nextTrainFromHigashiOmiyaToOmiya = null;
		
		for(Train1 train : train3) {
			if(train.getDepartureTime().isAfter(now)) {
				nextTrainFromHigashiOmiyaToOmiya = train;
				break;
			}
		}
		
		// 次の電車の情報を ModelAndView に追加
	    if (nextTrainFromHigashiOmiyaToOmiya != null) {
	        mav.addObject("Departure3", nextTrainFromHigashiOmiyaToOmiya.getDepartureTime());
	        mav.addObject("Arrival3", nextTrainFromHigashiOmiyaToOmiya.getArrivalTime());
	        mav.addObject("TrainName3", nextTrainFromHigashiOmiyaToOmiya.getName());
	    }
	    
	  //ファイルから電車のデータを読み込む(大宮駅から鴻巣駅に行く高崎線か上野東京ライン)
	    String filePath4 = "src\\main\\resources\\static\\txt\\Omiya-Konosu.txt";
	    List<Train1> trains4 = trainDataLoader.loadTrainData(filePath4);
	    
	    //東大宮から大宮駅に着いて次に乗り換える電車を定義する
	    Train1 nextTrainFromOmiyaToKonosu = null;
	    
	    //自分が東大宮駅からの乗ってきた電車が大宮駅に着いて乗換出来そうな鴻巣行きの電車を検索する
	    for(Train1 train : trains4) {
	    	if(train.getDepartureTime().isAfter(nextTrainFromHigashiOmiyaToOmiya.getArrivalTime())) {
	    		nextTrainFromOmiyaToKonosu = train;
	    		break;
	    	}
	    }
	    
	    //乗り換える電車の情報を ModelAndView に追加
	    if(nextTrainFromOmiyaToKonosu != null) {
	    	mav.addObject("Departure4", nextTrainFromOmiyaToKonosu.getDepartureTime());
	    	mav.addObject("Arrival4", nextTrainFromOmiyaToKonosu.getArrivalTime());
	    	mav.addObject("TrainName4", nextTrainFromOmiyaToKonosu.getName());
	    }
		
	    //遷移するページを追加
		mav.setViewName("HigashiOmiya-Konosu");
		return mav;
	}
	
	// TrainDataLoaderクラスのインスタンスを追加
    private TrainDataLoader trainDataLoader = new TrainDataLoader();
	
}