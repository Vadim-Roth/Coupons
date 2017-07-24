package com.coupons.facade;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.dao.dbimpl.CompanyDBDAO;
import com.coupons.dao.dbimpl.CouponDBDAO;
import com.coupons.exceptions.AllExceptions;

public class CompanyFacade implements CouponClientFacade{
	
	private CouponDBDAO couponDbdao = new CouponDBDAO();
	private CompanyDBDAO companyDbDao = new CompanyDBDAO();
	private static Company comp;
	
	//empty constructor
	public CompanyFacade() {
	}
	
	//checks whether a company exists
	private boolean checkExistence(String name, String password) throws AllExceptions{
		if(companyDbDao.getCompany(name) == null) return false;
		return true;
	}
	
	//checks whether a company matched the name and password to an existing company
	private boolean confirm(String name, String password) throws AllExceptions{
		if(companyDbDao.getCompany(name).getCompPassword().equals(password))
			return true;
		return false;
	}

	//login to company facade
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws AllExceptions { 

		if (clientType != ClientType.COMPANY) {
			System.out.println("Client type is not a COMPANY");
			throw new IndexOutOfBoundsException("Clinet type is not a COMPANY");
		}

		if(!checkExistence(name, password)){
			System.out.println("Company doens't exist with the name " + name);
		}
		else if (!confirm(name, password)) {
			System.out.println("Login failed. Not correct username or password.");
			throw new IndexOutOfBoundsException("Login failed. Not correct username or password.");
		}
		else{
			System.out.println("Login succeed");
			CompanyFacade facade = new CompanyFacade();
			comp = companyDbDao.getCompany(name);
			return facade;
		}
		return null;
	}
	
	//creates new company using the CompanyDBDAO
	public void createCompany(Company company) throws AllExceptions{
		companyDbDao.createCompany(company);
	}
	
	//creates new coupon using the CoouponDBDAO and assigns it to the creator
	public void createCoupon(Coupon coupon) throws AllExceptions {
		couponDbdao.createCoupon(coupon);
		System.out.println(companyDbDao.getCompany(3));
		System.out.println("hi");
		companyDbDao.assignCoupon(comp.getCompId(), coupon.getCouponId());
	}
	
	//deactivates a company and deactivates it's coupons
	public void removeCompany() throws AllExceptions {
		if(exists(comp))
			companyDbDao.removeCompany(comp);
		else
			nullPointer(comp);
	}
	
	// reactivates a deactivated company
	public void reactivateCompany() throws AllExceptions {
		if(exists(comp))
			companyDbDao.reactivateCompany(comp);
		else
			nullPointer(comp);
	}
	
	//updates a company
	public void updateCompany(Company updatedCompany) throws AllExceptions {
		if(exists(updatedCompany)){
			companyDbDao.updateCompany(updatedCompany);
			comp = updatedCompany;
		}
		else
			nullPointer(updatedCompany);
	}
	
	public Company getCompDetails(){
		System.out.println(comp);
		return comp;
	}
	
	//************************************************************************************************
	//******************************----private functions----*****************************************
	//************************************************************************************************
		
	
	//checks if we are dealing with null
	private boolean exists(Company company){
		if(company != null) return true;
		return false;
	}
	
	//prints error message accordingly
	private void nullPointer(Company company){
		System.out.println("The company doesn't exist!");
	}

}
