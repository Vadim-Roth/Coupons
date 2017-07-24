package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.exceptions.AllExceptions;

public interface CouponDAO {
	
	public void createCoupon(Coupon coupon) throws AllExceptions;
	public void updateCoupon(Coupon coupon) throws AllExceptions;
	public void removeCoupon(Coupon coupon) throws AllExceptions;
	public void deleteCoupon(Coupon coupon)throws AllExceptions;
	public Coupon getCoupon(long couponId) throws AllExceptions;
	public List<Coupon> getCoupons(String couponId) throws AllExceptions;
	public List<Coupon> getAllCoupons() throws AllExceptions;
	public List<Coupon> getCouponByType(CouponType type) throws AllExceptions;
	
}

