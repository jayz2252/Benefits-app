package com.speridian.benefits2.service;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speridian.benefits2.model.dao.INSPlanLoyaltyLevelsDao;
import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;


@Service
public class INSPlanLoyaltyServiceImpl implements INSPlanLoyaltyService{
	
@Autowired

INSPlanLoyaltyLevelsDao insPlanLoyaltyLevelsDao;

@Override
public INSPlanLoyaltyLevels insert(INSPlanLoyaltyLevels insPlanLoyaltyLevels) {
	// TODO Auto-generated method stub
	insPlanLoyaltyLevelsDao.insert(insPlanLoyaltyLevels);
	return null;
}

@Override
public INSPlanLoyaltyLevels get(Integer insPlanLoyaltyLevelId) {
	// TODO Auto-generated method stub
	
	return null;
}


	
}
