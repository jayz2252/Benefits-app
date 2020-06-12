package com.speridian.benefits2.service;

import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;

public interface INSPlanLoyaltyService {
	
	public INSPlanLoyaltyLevels insert(INSPlanLoyaltyLevels insPlanLoyaltyLevels);
	public INSPlanLoyaltyLevels get (Integer insPlanLoyaltyLevelId);

}
