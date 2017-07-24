package com.coupons.facade;

import com.coupons.exceptions.AllExceptions;

public interface CouponClientFacade {
	//the base interface of the facade. All the facades implement it

	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws AllExceptions;

}
