package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.HomePageNotificationDao;
import com.speridian.benefits2.model.pojo.HomePageNotification;

/**
 * 
 * @author ardra.madhu
 * 
 */

public class HomePageNotificationServiceImpl implements HomePageNotificationService {

	@Autowired
	HomePageNotificationDao homePageNotificationDao;

	@Override
	public List<String> listAll() {

		return homePageNotificationDao.listAll();
	}

	@Override
	public HomePageNotification get(Integer notificationID) {
		return homePageNotificationDao.get(notificationID);
	}

	@Override
	public void insert(HomePageNotification homePageNotification) {
	 homePageNotificationDao.insert(homePageNotification);
	}

	@Override
	public boolean update(HomePageNotification homePageNotification) {
		return homePageNotificationDao.update(homePageNotification);
	}

	@Override
	public boolean remove(Integer notificationID) {
		return homePageNotificationDao.delete(notificationID);
	}

	@Override
	public List<HomePageNotification> listAllNotifications() {
		// TODO Auto-generated method stub
		return homePageNotificationDao.listAllNotifications();
	}

}
