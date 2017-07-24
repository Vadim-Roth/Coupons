package com.coupons.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.dao.dbimpl.CompanyDBDAO;
import com.coupons.dao.dbimpl.CouponDBDAO;
import com.coupons.dao.dbimpl.CustomerDBDAO;
import com.coupons.exceptions.AllExceptions;
import com.coupons.facade.ClientType;;

public class AdminFacade implements CouponClientFacade{
	
	//an empty constructor
	public AdminFacade() {
	}

	//login to admin facade
	@Override
	public CouponClientFacade  login(String name, String password, ClientType clientType) 
			throws AllExceptions{

		if (clientType != ClientType.ADMIN) {
			System.out.println("Client type is not admin");
			throw new IndexOutOfBoundsException("Clinet type is not admin");
		}

		if (!name.equals("admin") || !password.equals("1234")) {
			System.out.println("Login failed. Not correct username or password.");
			throw new IndexOutOfBoundsException("Login failed. Not correct username or password.");
		}
		else{
			System.out.println("Login succeed");
			AdminFacade facade = new AdminFacade();
			return facade;
		}
	}
	
	//enables finding coupons by ID
	public Coupon getCoupon(long couponId) throws AllExceptions{
		CouponDBDAO couponDbdao = new CouponDBDAO();
		return couponDbdao.getCoupon(couponId);
	}
	
	//enables finding coupons by ID
	public List<Coupon> getCoupon(String couponName) throws AllExceptions{
		CouponDBDAO couponDbdao = new CouponDBDAO();
		return couponDbdao.getCoupons(couponName);
	}
	
	//************************************************************************************************
	//******************************----Company Functions----*****************************************
	//************************************************************************************************
	
	//creates new company using the CompanyDBDAO
	public void createCompany(Company company) throws AllExceptions{
		new CompanyFacade().createCompany(company);
	}
	
	//creates new coupon using the CoouponDBDAO and assigns it to a company
	public void createCoupon(Company company, Coupon coupon) throws AllExceptions {
		//new CompanyFacade().createCoupon(company, coupon);
	}
	
	//deactivates a company and deactivates it's coupons
	public void removeCompany(Company company) throws AllExceptions{
		//new CompanyFacade().removeCompany(company);
	}
	
	//reactivates a deactivated company
	public void reactivateCompany(Company company) throws AllExceptions{
		//new CompanyFacade().reactivateCompany(company);
	}

	//deletes a company and it's coupons completely 
	public void deleteCompany(Company company) throws AllExceptions{
		CouponDBDAO couponDbDao = new CouponDBDAO();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>(company.getCoupons());
		
		for(int i = 0; i < coupons.size(); i++){
			couponDbDao.deleteCoupon(coupons.get(i));			
		}		
		
		CompanyDBDAO companyDbDao = new CompanyDBDAO();
		companyDbDao.deleteCompany(company);
	}
	
	//updates a company
	public void updateCompany(Company updatedCompany) throws AllExceptions{
		new CompanyFacade().updateCompany(updatedCompany);	
	}
	
	//returns all companies 
	public Collection<Company> getAllCompanies() throws AllExceptions{
		return new CompanyDBDAO().getAllCompanies();
	}
	
	//returns a specific company by ID
	public Company getCompany(long id) throws AllExceptions{
		return new CompanyDBDAO().getCompany(id);
	}
	
	//returns a specific company by ID
	public Company getCompany(String name) throws AllExceptions{
		return new CompanyDBDAO().getCompany(name);
	}
	
	//************************************************************************************************
	//******************************----Customer Functions----****************************************
	//************************************************************************************************

	// creates new customer using the CustomerDBDAO
	public void createCustomer(Customer customer) throws AllExceptions {
		new CustomerFacade().createCustomer(customer);
	}
	
	public void purchaseCoupon(Customer customer, Coupon coupon) throws AllExceptions{
		new CustomerFacade().purchaseCoupon(customer, coupon);
	}

	// deactivates a customer and deactivates it's coupons
	public void removeCustomer(Customer customer) throws AllExceptions {
		new CustomerFacade().removeCustomer(customer);
	}

	// reactivates a deactivated customer
	public void reactivateCustomer(Customer customer) throws AllExceptions {
		new CustomerFacade().reactivateCustomer(customer);
	}

	// deletes a customer and it's coupons completely
	public void deleteCustomer(Customer customer) throws AllExceptions {
		CouponDBDAO couponDbDao = new CouponDBDAO();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>(customer.getCoupons());

		for (int i = 0; i < coupons.size(); i++) {
			couponDbDao.deleteCoupon(coupons.get(i));
		}

		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		customerDbDao.deleteCustomer(customer);
	}

	// updates a customer
	public void updateCustomer(Customer updatedCustomer) throws AllExceptions {
		new CustomerFacade().updateCustomer(updatedCustomer);
	}

	// returns all customers
	public Collection<Customer> getAllCustomers() throws AllExceptions {
		return new CustomerFacade().getAllCustomers();
	}

	// returns a specific customer by ID
	public Customer getCustomer(long id) throws AllExceptions {
		return new CustomerFacade().getCustomer(id);
	}
	
	// returns a specific customer by name
	public Customer getCustomer(String custName) throws AllExceptions {
		return new CustomerFacade().getCustomer(custName);
	}

}
