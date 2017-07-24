package com.coupons.dao;


import java.util.List;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.exceptions.AllExceptions;

public interface CustomerDAO {
	
	public void createCustomer(Customer customer) throws AllExceptions;
	public void updateCustomer(Customer customer) throws AllExceptions;
	public void removeCustomer(Customer customer) throws AllExceptions;
	public void ractivateCustomer(Customer customer) throws AllExceptions;
	public void deleteCustomer(Customer customer) throws AllExceptions;
	public void purchaseCoupon(long custId, long couponId) throws AllExceptions;
	public Customer getCustomer(long custId) throws AllExceptions;
	public Customer getCustomer(String custName) throws AllExceptions;
	public List<Customer> getAllCustomers() throws AllExceptions;
	public List<Coupon> getCoupons(long custId) throws AllExceptions;
	public Customer login(String custName, String password) throws AllExceptions;
	
}
