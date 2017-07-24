package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.exceptions.AllExceptions;

public interface CompanyDAO {
	
	public void createCompany(Company company) throws AllExceptions;
	public void updateCompany(Company company) throws AllExceptions;
	public void removeCompany(Company company) throws AllExceptions;
	public void reactivateCompany(Company company) throws AllExceptions;
	public void deleteCompany(Company company) throws AllExceptions;
	public void assignCoupon(long companyId, long couponId) throws AllExceptions;
	public Company getCompany(long compId) throws AllExceptions;
	public Company getCompany(String compName) throws AllExceptions;
	public List<Company> getAllCompanies() throws AllExceptions;
	public List<Coupon> getCoupons(long custId) throws AllExceptions;
	public Company login(String custName, String password) throws AllExceptions;
	
}

