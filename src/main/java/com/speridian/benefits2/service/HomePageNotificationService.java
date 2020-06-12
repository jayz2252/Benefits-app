package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.HomePageNotification;

public interface HomePageNotificationService {

	
	public List<String> listAll();
	public HomePageNotification get(Integer notificationID);
	
	public void insert(HomePageNotification homePageNotification);
	public boolean update(HomePageNotification homePageNotification);
	boolean remove(Integer notificationID);
	public List<HomePageNotification> listAllNotifications();
}
