package com.coupons.tests;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.beans.Customer;
import com.coupons.dao.CompanyDAO;
import com.coupons.dao.CouponDAO;
import com.coupons.dao.CustomerDAO;
import com.coupons.dao.dbimpl.CompanyDBDAO;
import com.coupons.dao.dbimpl.CouponDBDAO;
import com.coupons.dao.dbimpl.CustomerDBDAO;
import com.coupons.facade.ClientType;
import com.coupons.facade.CouponClientFacade;
public class TestDAO {
	public static void main(String[] args) throws Exception{
		
		List<Coupon> list = new ArrayList<Coupon>();
		
		String start = "2020-5-5";
		String end = "2020-4-4";
		String start2 = "2019-8-8";
		String end2 = "2021-8-8";
		
		//~~~~~~~~~****Customer****~~~~~~~~
		/*
		CustomerDAO customerDao = new CustomerDBDAO();
		Customer customer = new Customer(1, "Johny", "heres", list);
		Customer customer2 = new Customer(customer.getCustId(), "Shang", "Tsung", list);
		Customer customer3 = new Customer(2, "Bicycle", "Kick", list);
		
		//customerDao.createCustomer(customer);	
		//customerDao.updateCustomer(customer2);
		//customerDao.removeCustomer(customer);
		//customerDao.ractivateCustomer(customer);
		//customerDao.deleteCustomer(customer);
		
		customer2 = customerDao.getCustomer(customer.getCustId());
		//System.out.println(customer2.toString());
		//System.out.println(customerDao.login("Johny", "heres"));
		//customerDao.createCustomer(customer3);
		//System.out.println(customerDao.getAllCustomers());
		//customerDao.purchaseCoupon(1, 1);
		System.out.println(customerDao.getCoupons(1));
		*/
		
		//~~~~~~~~~****Company****~~~~~~~~
		
		CompanyDAO companyDao = new CompanyDBDAO();
		CouponDAO couponDao = new CouponDBDAO();
		Company company1 = new Company(1,"Aperture", "borealis", "cave@jhonson.com", list);
		Company company2 = new Company(3,"Black Mesa", "Xen", 
				"postapocalyptic@invasion.com", list);
		
		//companyDao.createCompany(company1);
		//companyDao.updateCompany(company1);
		//companyDao.removeCompany(company1);
		//companyDao.reactivateCompany(company1);
		//companyDao.deleteCompany(company1);
		
		//company2 = companyDao.getCompany(company1.getCompId());
		//companyDao.createCompany(new Company(2, "Black Mesa", "surrender", "combines@ftw.com", list));
		//System.out.println(companyDao.getAllCompanies().toString());
		//System.out.println(companyDao.login("Aperture", "bore4alis"));
		//System.out.println(companyDao.getCoupons(1));
		//couponDao.createCoupon(new Coupon("miriam", "lol", java.sql.Date.valueOf(start),
		//		java.sql.Date.valueOf(end), 2, CouponType.CAMPING, "ho", 14, "boom"));
		companyDao.assignCoupon(3, couponDao.getCoupons("lol").get(0).getCouponId());
		//System.out.println(companyDao.getCompany("Black Mesa"));
		//System.out.println(companyDao.getCompany(1));
		//companyDao.login("Black Mesa", "Xen");
		
		
		//~~~~~~~~~****Coupon****~~~~~~~~
		/*
		String img = "http://vignette4.wikia.nocookie.net/"
				+ "spongebob/images/d/d4/Krabby_Patty_transparentpng.png/revision/"
				+ "latest?cb=20170310181007;";
		String img2 = "http://vignette2.wikia.nocookie.net/nickelodeon/images/2/20/"
				+ "Thechumbucket.jpg/revision/latest?cb=20151004134737";
		String img3 = "http://vignette1.wikia.nocookie.net/spongebob/images/7/7f"
				+ "/Kelp_Shake_Store.PNG/revision/latest?cb=20120728222915";
		
		
		Coupon coupon = new Coupon(1, "Krusty Krab", java.sql.Date.valueOf(start),
				java.sql.Date.valueOf(end), 2, CouponType.FOOD, "104 Pattie free", 2.00, img);
		Coupon coupon2 = new Coupon(2, "Chum Bucket", java.sql.Date.valueOf(start2),
				java.sql.Date.valueOf(end2), 3, CouponType.RESTAURANTS, "4 puke bags 4 for 1$", 4.00,
				img2);
		Coupon coupon3 = new Coupon(2, "Kelpshake", java.sql.Date.valueOf(start2),
				java.sql.Date.valueOf(end2), 4,	CouponType.RESTAURANTS, "5 Kelpshakes for 5$", 5.00,
				img2);
		
		
		//couponDao.createCoupon(coupon3);
		//couponDao.updateCoupon(coupon3);
		//couponDao.removeCoupon(coupon2);
		//couponDao.deleteCoupon(coupon2);
		
		//System.out.println(couponDao.getCoupon(1).toString());
		//couponDao.createCoupon(coupon3);
		//System.out.println(couponDao.getAllCoupons());
		System.out.println(couponDao.getCouponByType(CouponType.RESTAURANTS));
		
		*/
	}
}
	


