package com.coupons.tests;

import java.util.Random;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.beans.Customer;
import com.coupons.exceptions.AllExceptions;
import com.coupons.facade.AdminFacade;
import com.coupons.facade.ClientType;
import com.coupons.facade.CompanyFacade;
import com.coupons.facade.CustomerFacade;
import com.coupons.system.CouponSystemSingleton;

public class TestFacade {
	
	public static void main(String[] args) throws AllExceptions{
		
		CouponSystemSingleton system = CouponSystemSingleton.getInstance();
		
		//AdminFacade facade = (AdminFacade) system.login("admin", "1234", ClientType.ADMIN); 
		
		CompanyFacade facade = (CompanyFacade) system.login("Black Mesa", "Xen", ClientType.COMPANY);
		
		//CustomerFacade custFacade = (CustomerFacade) system.login("Bicycle", "Kick", ClientType.CUSTOMER);
		
		
		String img = "http://vignette4.wikia.nocookie.net/"
				+ "spongebob/images/d/d4/Krabby_Patty_transparentpng.png/revision/"
				+ "latest?cb=20170310181007;";
		String start = "2020-5-5";
		String end = "2020-4-4";
		Coupon coupon = new Coupon("lamma", "Krusty Krab", java.sql.Date.valueOf(start),
				java.sql.Date.valueOf(end), 2, CouponType.FOOD, "104 Pattie free", 2.00, img);
		
		//~~~~~~~~~****Company****~~~~~~~~
		
		//Company company = facade.getCompany("Jason");
		//System.out.println(facade.getAllCompanies());
		//facade.deleteCompany();
		
		//facade.getCompDetails();
		//facade.createCompany(new Company("Jason", "freddie","hell@gmail.com"));
		//facade.updateCompany(new Company(facade.getCompany("Jason").getCompId(), "Black Mesa	", "zxcas",
		//		"as0", facade.getCompany("Jason").getCoupons()));
		//facade.removeCompany();
		//facade.reactivateCompany();
		//System.out.println(facade.getCompany("Jason"));
		facade.createCoupon(coupon);
		
		
		//~~~~~~~~~****Customer****~~~~~~~~
		/*
		Customer customer = facade.getCustomer("Johny");

		//facade.createCustomer(new Customer("James", "Hawk"));
		//facade.updateCustomer(new Customer(facade.getCustomer("James").getCustId(), "Odin kin",
		//	"Molag bal", facade.getCustomer("James").getCoupons()));
		//facade.removeCustomer(customer);
		//facade.reactivateCustomer(customer);
		//facade.deleteCustomer(customer);
		//System.out.println(facade.getCustomer("James"));
		//facade.purchaseCoupon(facade.getCustomer("Johny"), facade.getCoupon("Krusty Krab").get(0));
		//System.out.println(facade.getAllCustomers());
		 */
		
	}
}
