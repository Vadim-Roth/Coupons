package com.coupons.facade;

import java.util.Collection;

import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.dao.dbimpl.CustomerDBDAO;
import com.coupons.exceptions.AllExceptions;

public class CustomerFacade implements CouponClientFacade{
	
	//empty constructor
	public CustomerFacade(){	
	}
	
	//checks whether a customer exists
	private boolean checkExistence(String name, String password) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		if (customerDbDao.getCustomer(name) == null)
			return false;
		return true;
	}

	// checks whether a customer matched the name and password to an existing
	// customer
	private boolean confirm(String name, String password) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		if (customerDbDao.getCustomer(name).getCustPassword().equals(password))
			return true;
		return false;
	}
	
	//login to customer facade
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws AllExceptions {

		if (clientType != ClientType.CUSTOMER) {
			System.out.println("Client type is not a CUSTOMER");
			throw new IndexOutOfBoundsException("Clinet type is not a CUSTOMER");
		}

		if (!checkExistence(name, password)) {
			System.out.println("Customer doens't exist with the name " + name);
		}
		else if(!confirm(name, password)){
			System.out.println("Login failed. Not correct username or password.");
			throw new IndexOutOfBoundsException("Login failed. Not correct username or password.");
		}
		else{
			System.out.println("Login succeed");
			CustomerFacade facade = new CustomerFacade();
			return facade;
		}
		return null;
	}
	
	// creates new customer using the CustomerDBDAO
	public void createCustomer(Customer customer) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		customerDbDao.createCustomer(customer);
	}

	//purchases a coupon for the customer
	public void purchaseCoupon(Customer customer, Coupon coupon) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		customerDbDao.purchaseCoupon(customer.getCustId(), coupon.getCouponId());
	}

	// deactivates a customer and deactivates it's coupons
	public void removeCustomer(Customer customer) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		if(exists(customer))
			customerDbDao.removeCustomer(customer);
		else
			nullPointer(customer);
	}

	// reactivates a deactivated customer
	public void reactivateCustomer(Customer customer) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		if(exists(customer))
			customerDbDao.ractivateCustomer(customer);
		else
			nullPointer(customer);
	}
	
	// updates a customer
	public void updateCustomer(Customer updatedCustomer) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		if(exists(updatedCustomer))
			customerDbDao.updateCustomer(updatedCustomer);
		else
			nullPointer(updatedCustomer);
	}

	// returns all customers
	public Collection<Customer> getAllCustomers() throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		return customerDbDao.getAllCustomers();
	}

	// returns a specific customer by ID
	public Customer getCustomer(long custId) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		return customerDbDao.getCustomer(custId);
	}
	
	// returns a specific customer by name
	public Customer getCustomer(String custName) throws AllExceptions {
		CustomerDBDAO customerDbDao = new CustomerDBDAO();
		return customerDbDao.getCustomer(custName);
	}
	
	// checks if we are dealing with null
	private boolean exists(Customer customer) {
		if (customer != null)
			return true;
		return false;
	}

	// prints error message accordingly
	private void nullPointer(Customer customer) {
		System.out.println("The customer doesn't exist!");
	}
}
