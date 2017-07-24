package com.coupons.dao.dbimpl;

import static com.coupons.beans.Constants.COMPANY;
import static com.coupons.beans.Constants.COMPANY_COUPON;
import static com.coupons.beans.Constants.COUPON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.dao.CompanyDAO;
import com.coupons.exceptions.AllExceptions;
import com.coupons.system.CouponSystemSingleton;
public class CompanyDBDAO implements CompanyDAO {

	
	//private static ConnectionPool pool = ConnectionPool.getConnection();
	
	private Connection getConnection() throws SQLException {
		return CouponSystemSingleton.getConnectionPool().getConnection();
		
		// TODO: maybe we should catch the exception here
		// and close the program. It is too severe
		
		///
		/*String dbName = "coupons";
		String url = "jdbc:mysql://localhost/" + dbName + "?useSSL=true";
		return DriverManager.getConnection(url, "root", "1234");
		*/
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

	//creates a new company 
	@Override
	public void createCompany(Company company) throws AllExceptions {
		//checks whether there is already an existing company with the same ID
		if(getCompany(company.getCompId()) != null){
			String msg = "Company with the ID " + company.getCompId() + " already exists!";
			System.out.println(msg);
		//checks whether there is already an existing company with the same name
		} else if(existingName(company.getCompName())){
			String msg = "Company with the name " + company.getCompName() + " already exists!";
			System.out.println(msg);
		}else{
			// get a connection (from pool)
			Connection con = null;
			try {
				con = getConnection();

				// create SQL insert
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO " + COMPANY + " " + "VALUES" + " (?, ?, ?, ?, 1)");
				stat.setLong(1, company.getCompId());
				stat.setString(2, company.getCompName());
				stat.setString(3, company.getCompPassword());
				stat.setString(4, company.getEmail());

				System.out.println("New company created with ID = " + company.getCompId());
				stat.executeUpdate();

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");

				throw new AllExceptions("", e);
			}
		}
	}

	//updates a company
	@Override
	public void updateCompany(Company company) throws AllExceptions {
		//checks existence
		if(getCompany(company.getCompId()) == null){
			nonExistentCompany(company.getCompId());
		}
		else{
			if(getCompany(company.getCompId()) != null){
				String msg = "Company with the name " + company.getCompName() +
						" already exists!";
				System.out.println(msg);
			}
			// get a connection (from pool)
			Connection con = null;

			try {
				
				con = getConnection();

				// update SQL
				PreparedStatement stat = con.prepareStatement("UPDATE " + COMPANY + " SET "
						+ "COMP_NAME=?, PASSWORD=?, EMAIL=? WHERE COMP_ID=" + company.getCompId());
				stat.setString(1, company.getCompName());
				stat.setString(2, company.getCompPassword());
				stat.setString(3, company.getEmail());

				stat.executeUpdate();
				System.out.println("Company with ID = " + company.getCompId() + " updated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}

	//deactivates a company
	@Override
	public void removeCompany(Company company) throws AllExceptions {
		//checks existence
		if (getCompany(company.getCompId()) == null) {
			nonExistentCompany(company.getCompId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// deactivation SQL
				Statement stat = con.createStatement();
				String sql = ("UPDATE " + COMPANY + " SET ACTIVE='0' WHERE COMP_ID=" + 
						company.getCompId());
				stat.executeUpdate(sql);
				sql = ("DELETE FROM " + COMPANY_COUPON + " WHERE COMP_ID=" + company.getCompId());
				sql = ("DELETE FROM " + COUPON + " WHERE COUPON_ID=" + company.getCompId());
				stat.executeUpdate(sql);
				System.out.println("Company with ID = " + company.getCompId() + " removed along with"
						+ " his coupons!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}

	//reactivates a company
	@Override
	public void reactivateCompany(Company company) throws AllExceptions {
		if (getCompany(company.getCompId()) == null) {
			nonExistentCompany(company.getCompId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// reactivation SQL
				Statement stat = con.createStatement();
				String sql = ("UPDATE " + COMPANY + " SET ACTIVE='1' WHERE COMP_ID=" + 
						company.getCompId());
				stat.executeUpdate(sql);
				System.out.println("Company with ID = " + company.getCompId() + " reactivated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}

	//deletes a company
	public void deleteCompany(Company company) throws AllExceptions {
		//checks existence
		if (getCompany(company.getCompId()) == null) {
			nonExistentCompany(company.getCompId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();
				// delete SQL
				Statement stat = con.createStatement();
				String sql = ("DELETE FROM " + COMPANY + " WHERE COMP_ID=" + company.getCompId());
				stat.executeUpdate(sql);
				sql = ("DELETE FROM " + COMPANY_COUPON + " WHERE COMP_ID=" + company.getCompId());
				stat.executeUpdate(sql);
				System.out.println("Company with ID = " + company.getCompId() + " deleted along"
					+ " with his coupons!");

				// release connection
				releaseConnection(con);
			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();

			}
		}
	}
	
	//assigns a coupon to a company
	@Override
	public void assignCoupon(long compId, long couponId) throws AllExceptions {
		// checks existence

		System.out.println(getCompany(compId));
		if (getCompany(compId) == null) {
			nonExistentCompany(compId);
		}
		else if(!existingCoupon(couponId)){
			nonExistentCoupon(couponId);
		}
		else if(assignedCoupon(couponId)){
			System.out.println("Coupon is already assigned - (" + compId + ", " + couponId + 
					")");
		}
		else{
			// add coupon to joined table in DB
			Connection con = null;
			try {
				con = getConnection();
				// create SQL insert
				PreparedStatement stat = con.prepareStatement("INSERT INTO " + COMPANY_COUPON 
						+ " VALUES" + " (?, ?)");
				System.out.println();
				stat.setLong(1, compId);
				stat.setLong(2, couponId);
				stat.executeUpdate();
				System.out.println("New company coupon - (" + compId + ", " + couponId + 
						")");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");

				throw new AllExceptions("", e);
			}
		}
	}

	//returns a specific company by ID
	@Override
	public Company getCompany(long compId) throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		Company company = null;
		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COMPANY + " WHERE COMP_ID=" + compId);
			ResultSet rs = stat.executeQuery(sql);
			company = new Company(compId);

			while (rs.next()) {
				// from company table
				
				company.setCompName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));

				if (rs.getString("ACTIVE").equals(0)) {
					company.deactivate();
				}
				company.setCoupons(getCoupons(rs.getLong("COMP_ID")));
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);

		//check if any real company returned
		if(company.getCompName() == null) return null;
		return company;
	}
	
	//returns a specific company by name
	@Override
	public Company getCompany(String compName) throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		Company company = null;

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COMPANY + " WHERE COMP_NAME='" + compName + "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from company table
				company = new Company(rs.getLong("COMP_ID"));
				company.setCompName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));
				if (rs.getString("ACTIVE").equals(0)) {
					company.deactivate();
				}
				company.setCoupons(getCoupons(rs.getLong("COMP_ID")));
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		// check if any real company returned
		if (company == null){
			return null;
		}
		return company;
	}


	//returns all companies
	@Override
	public List<Company> getAllCompanies() throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		List<Company> list = new ArrayList<Company>();

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COMPANY + " WHERE ACTIVE='1'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from company table
				list.add(new Company(rs.getLong("COMP_ID"), rs.getString("COMP_NAME"), 
						rs.getString("PASSWORD"),rs.getString("EMAIL"), getCoupons(
								rs.getLong("COMP_ID"))));
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
	public List<Coupon> getCoupons(long compId) throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();

			// make a new list with coupon details

			String sql = ("SELECT * FROM " + COUPON + " WHERE COUPON_ID in (SELECT coupon_Id"
					+ " from " + COMPANY_COUPON + " where comp_Id like " + compId + ")");
			ResultSet rs = stat.executeQuery(sql);
			System.out.println(couponList.size());
			while (rs.next()) {
				// add coupons with full deatails to the list
				couponList.add(new Coupon(rs.getLong("COUPON_ID"), rs.getString("CREATOR"),
						 rs.getString("TITLE"),rs.getDate("START_DATE"),rs.getDate("END_DATE"),
						 rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")),
						 	rs.getString("MESSAGE"), rs.getDouble("PRICE"), rs.getString("IMAGE")));

			}

		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		return couponList;
	}

	//the dbCheck for login, returns company
	@Override
	public Company login(String compName, String password) throws AllExceptions {
		// getting a connection from the Connection Pool
		Connection connection = null;
		try {
			connection = getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			Company company = null;
			
			// checking if the there is a Company with the login NAME and PASSWORD
			ResultSet results = connection.createStatement().executeQuery("SELECT COMP_ID FROM "
			+ COMPANY + " WHERE COMP_NAME like '" + compName + "' AND PASSWORD like '" + password + "'");
			
			if(results.next()){
				
				company = getCompany(results.getLong("COMP_ID"));
			}
			
			else {
				System.out.println("CompanyDBDAO:\nThe company name or password are incorrect!");
			}
			System.out.println("CompanyDBDAO: company = " + company);
			return company;
		}
		
		catch (SQLException e){
			
			throw new AllExceptions("CompanyDBDAO:\nAn error occured while a company tried to login: "
			+ e.getMessage());
		}
	}
	
	//checks whether the company really exists based on ID
	private void nonExistentCompany(long compId){
		String msg = "Company with the ID " + compId + " doesn't exist!";
		System.out.println(msg);
	}
	
	// checks whether the company really exists based on ID
	private void nonExistentCoupon(long couponId) {
		String msg = "Coupon with the ID " + couponId + " doesn't exist!";
		System.out.println(msg);
	}
	
	//checks whether a certain company name already exists
	private boolean existingName(String compName) throws AllExceptions{
		List<Company> list = getAllCompanies();
		for(Company company : list){
			if(company.getCompName().equals(compName)) return true;
		}
		return false;
	}
	
	//checks whether the coupon already assigned to the company
	private boolean assignedCoupon(long couponId){

		// get a connection (from pool)
		Connection con = null;
		List<Long> couponList = new ArrayList<Long>();
		
		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();

			// make a new list to check if there is anything

			String sql = ("SELECT * FROM " + COMPANY_COUPON +" where COUPON_ID = '" + couponId 
					+ "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// adds a company if there is one
				couponList.add(rs.getLong("COMP_ID"));
			}

		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
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
