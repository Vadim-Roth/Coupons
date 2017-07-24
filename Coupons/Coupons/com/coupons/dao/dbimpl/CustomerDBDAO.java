package com.coupons.dao.dbimpl;

import static com.coupons.beans.Constants.COUPON;
import static com.coupons.beans.Constants.CUSTOMER;
import static com.coupons.beans.Constants.CUSTOMER_COUPON;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.beans.Customer;
import com.coupons.dao.CustomerDAO;
import com.coupons.exceptions.AllExceptions;
import com.coupons.system.CouponSystemSingleton;

public class CustomerDBDAO implements CustomerDAO {
	
	
	private Connection getConnection() throws SQLException {
		// TODO: maybe we should catch the exception here
		// and close the program. It is too severe
		return CouponSystemSingleton.getConnectionPool().getConnection();
	}

	private void releaseConnection(Connection con) {
		// We currently are closing the connections. Later we
		// will move to a better solution using a connection pool
		// that Assaf will provide
		try {
			con.close();
		} catch (SQLException e) {
			// We don't care
			e.printStackTrace();
		}
	}
	
	//creates a new customer
	@Override
	public void createCustomer(Customer customer) throws AllExceptions {
		// checks whether there is already an existing company with the same ID
		if (getCustomer(customer.getCustId()) != null) {
			String msg = "Company with the ID " + customer.getCustId() + " already exists!";
			System.out.println(msg);
		// checks whether there is already an existing company with the same name
		} else if (existingName(customer.getCustName())) {
			String msg = "Customer with the name " + customer.getCustName() + " already exists!";
			System.out.println(msg);
		} else {
			// get a connection (from pool)
			Connection con = null;
			try {
				con = getConnection();

				// create SQL insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + CUSTOMER + " VALUES"
						+ " (?, ?, ?, 1)");
				stat.setLong(1, customer.getCustId());
				stat.setString(2, customer.getCustName());
				stat.setString(3, customer.getCustPassword());

				System.out.println("New customer created with ID = " + customer.getCustId());
				stat.executeUpdate();

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			} finally {
				// release connection
				releaseConnection(con);
			}
		}
	}
	
	//updates a customer
	@Override
	public void updateCustomer(Customer customer) throws AllExceptions {
		// checks existence
		if (getCustomer(customer.getCustId()) == null) {
			nonExistentCustomer(customer.getCustId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// update SQL
				PreparedStatement stat = con.prepareStatement("UPDATE " + CUSTOMER + " SET "
						+ "CUST_NAME=?, PASSWORD=? WHERE CUST_ID=" + customer.getCustId());
				stat.setString(1, customer.getCustName());
				stat.setString(2, customer.getCustPassword());

				stat.executeUpdate();
				System.out.println("Customer with ID = " + customer.getCustId() + " updated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}
	
	//deactivates a customer
	@Override
	public void removeCustomer(Customer customer) throws AllExceptions {
		// check if the customer already exists
		if (getCustomer(customer.getCustId()) == null) {
			nonExistentCustomer(customer.getCustId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// deactivation SQL
				Statement stat = con.createStatement();
				String sql = ("UPDATE " + CUSTOMER + " SET ACTIVE='0' WHERE CUST_ID=" + 
						customer.getCustId());
				stat.executeUpdate(sql);
				System.out.println("Customer with ID = " + customer.getCustId() + " deactivated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();

			}
		}
	}

	//reactivates a customer
	@Override
	public void ractivateCustomer(Customer customer) throws AllExceptions {
		if (getCustomer(customer.getCustId()) == null) {
			nonExistentCustomer(customer.getCustId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// reactivation SQL
				Statement stat = con.createStatement();
				String sql = ("UPDATE " + CUSTOMER + " SET ACTIVE='1' WHERE CUST_ID=" + 
						customer.getCustId());
				stat.executeUpdate(sql);
				System.out.println("Customer with ID = " + customer.getCustId() + " reactivated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}
	
	//deletes a company
	public void deleteCustomer(Customer customer) throws AllExceptions {

		// checks existence
		if (getCustomer(customer.getCustId()) == null) {
			nonExistentCustomer(customer.getCustId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();
				// delete SQL
				Statement stat = con.createStatement();
				String sql = ("DELETE FROM " + CUSTOMER + " WHERE CUST_ID=" + customer.getCustId());
				stat.executeUpdate(sql);
				sql = ("DELETE FROM " + CUSTOMER_COUPON + " WHERE CUST_ID=" + customer.getCustId());
				stat.executeUpdate(sql);
				System.out.println("Customer with ID = " + customer.getCustId() + " deleted along"
						+ " with his coupons!");
				// release connection
				releaseConnection(con);
			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}

	//purchases a coupon to a customer
	@Override
	public void purchaseCoupon(long custId, long couponId) throws AllExceptions {
		// checks existence
		if (getCustomer(custId) == null) {
			nonExistentCustomer(custId);
		}
		else if(!existingCoupon(couponId)){
			nonExistentCoupon(couponId);
		}
		 else if (assignedCoupon(couponId)) {
			System.out.println("Coupon is already assigned - (" + custId + ", " + couponId + ")");
		} else {
			// get a connection (from pool)
			Connection con = null;
			try {
				con = getConnection();

				// create SQL insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + CUSTOMER_COUPON +
						" VALUES (?, ?)");
				stat.setLong(1, custId);
				stat.setLong(2, couponId);
				stat.executeUpdate();
				System.out.println("hi");

				// add coupon to company collection
				Coupon coupon2 = new CouponDBDAO().getCoupon(couponId);
				getCustomer(custId).getCoupons().add(coupon2);
				System.out.println("New customer coupon - (" + custId + ", " + couponId + 
						")");

				// release connection
				releaseConnection(con);
			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");

				throw new AllExceptions("", e);
			}
		}
	}
	
	//returns a specific customer by ID
	@Override
	public Customer getCustomer(long custId) throws AllExceptions {
		
		// get a connection (from pool)
		Connection con = null;
		Customer customer = null;

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + CUSTOMER + " WHERE CUST_ID=" + custId);
			ResultSet rs = stat.executeQuery(sql);
			customer = new Customer(custId);
			while (rs.next()) {
				// from customer table
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setCustPassword(rs.getString("PASSWORD"));
				if (rs.getString("ACTIVE").equals(0)) {
					customer.deactivate();
				}
				customer.setCoupons(getCoupons(rs.getLong("CUST_ID")));
			}

		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		//check if any real customer returned
		if(customer.getCustName() == null) return null;
		return customer;
	}
	
	// returns a specific customer by name
	@Override
	public Customer getCustomer(String custName) throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		Customer customer = null;

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + CUSTOMER + " WHERE CUST_NAME='" + custName + "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from customer table
				customer = new Customer(rs.getLong("CUST_ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setCustPassword(rs.getString("PASSWORD"));
				if (rs.getString("ACTIVE").equals(0)) {
					customer.deactivate();
				}
				customer.setCoupons(getCoupons(rs.getLong("CUST_ID")));
			}

		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		// check if any real customer returned
		if (customer == null)
			return null;
		return customer;
	}


	
	//returns all customers
	@Override
	public List<Customer> getAllCustomers() throws AllExceptions {
		
		// get a connection (from pool)
		Connection con = null;
		List<Customer> list = new ArrayList<Customer>();

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + CUSTOMER + " WHERE ACTIVE='1'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from customer table
				list.add(new Customer(rs.getLong("CUST_ID"), rs.getString("CUST_NAME"), 
						rs.getString("PASSWORD"), getCoupons(rs.getLong("CUST_ID"))));
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		return list;
	}

	//return all coupons
	@Override
	public List<Coupon> getCoupons(long custId) throws AllExceptions {
		
		// get a connection (from pool)
		Connection con = null;
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			
			
			//make a new list with coupon details
			String sql = ("SELECT * FROM " + COUPON + " WHERE COUPON_ID in " +
					"(SELECT coupon_Id from "	+ CUSTOMER_COUPON + " where cust_Id like " +
					custId + ")");
				ResultSet rs = stat.executeQuery(sql);
				while (rs.next()) {
					// add coupons with full deatails to the list 
					couponList.add(new Coupon(rs.getLong("COUPON_ID"), rs.getString("TITLE"),
							rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
								rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")),
									rs.getString("MESSAGE"), rs.getDouble("PRICE"), 
										rs.getString("IMAGE")));
				}
			
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		return couponList;
	}

	//the dbCheck for login, returns customer
	@Override
	public Customer login(String custName, String password) throws AllExceptions {
		// getting a connection from the Connection Pool
		Connection connection = null;
		try {
			connection = getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
				}
			try {
				Customer customer = null;
				// checking if the there is a Customer with the login NAME and PASSWORD
				ResultSet results = connection.createStatement().executeQuery("SELECT CUST_ID FROM "
				+ CUSTOMER + " WHERE CUST_NAME = '" + custName + "' AND PASSWORD = '" + password + "'");
				if(results.next()){
					customer = getCustomer(results.getLong("CUST_ID"));
				}
				else {
					System.out.println("CustomerDBDAO:\nThe customer name or password are incorrect!");
				}
				System.out.println("CustomerDBDAO: customer = " + customer);
					return customer;
				}
				catch (SQLException e){
					throw new AllExceptions("CustomerDBDAO:\nAn error occured while a customer"
							+ " tried to login: "+ e.getMessage());
		}
	}
	
		//checks whether the customer really exists based on ID
		private void nonExistentCustomer(long custId){
			String msg = "Customer with the ID " + custId + " doesn't exist!";
			System.out.println(msg);
		}
		
		// checks whether the company really exists based on ID
		private void nonExistentCoupon(long couponId) {
			String msg = "Coupon with the ID " + couponId + " doesn't exist!";
			System.out.println(msg);
		}
		
		//checks whether a certain customer name already exists
		private boolean existingName(String custName) throws AllExceptions{
			List<Customer> list = getAllCustomers();
			for(Customer customer : list){
				if(customer.getCustName().equals(custName)) return true;
			}
			return false;
		}
		
		//checks whether the coupon already assigned to the customer
		private boolean assignedCoupon(long couponId){

			// get a connection (from pool)
			Connection con = null;
			List<Long> couponList = new ArrayList<Long>();
			
			try {
				con = getConnection();
				// create SQL select
				Statement stat = con.createStatement();

				// make a new list with coupon details
				String sql = ("SELECT * FROM " + CUSTOMER_COUPON +" where COUPON_ID = '" + couponId 
						+ "'");
				ResultSet rs = stat.executeQuery(sql);
				while (rs.next()) {
					// adds a company if there is one
					couponList.add(rs.getLong("CUST_ID"));
				}

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
				e.printStackTrace();
			}
			releaseConnection(con);
			
			System.out.println(couponList.size());
			if(couponList.size() > 0) return true;
			return false;
		}
		
	//checks whether the coupon exists
	private boolean existingCoupon(long couponId) {

		// get a connection (from pool)
		Connection con = null;
		List<Long> couponList = new ArrayList<Long>();

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();

			// make a new list to check if there is anything

			String sql = ("SELECT * FROM " + COUPON + " where COUPON_ID = '" + couponId + "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// adds a coupon if there is one
				couponList.add(rs.getLong("COUPON_ID"));
			}

		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		if (couponList.size() > 0)
			return true;
		return false;
	}


}
