package com.coupons.dao.dbimpl;

import static com.coupons.beans.Constants.COUPON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.beans.CouponType;
import com.coupons.dao.CouponDAO;
import com.coupons.exceptions.AllExceptions;
import com.coupons.system.CouponSystemSingleton;

public class CouponDBDAO implements CouponDAO {

	
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

	//creates a new coupon
	@Override
	public void createCoupon(Coupon coupon) throws AllExceptions {
		// checks whether there is already an existing coupon with the same ID
		if (getCoupon(coupon.getCouponId()) != null) {
			String msg = "Coupon with the ID " + coupon.getCouponId() + " already exists!";
			System.out.println(msg);
			// checks whether there is already an existing coupon with the same name
		} else if (existingName(coupon.getTitle())) {
			String msg = "Coupon with the title " + coupon.getTitle() + " already exists!";
			System.out.println(msg);
		} else {
			// get a connection (from pool)
			Connection con = null;
			try {
				con = getConnection();

				// create SQL insert
				PreparedStatement stat = con
						.prepareStatement("INSERT INTO " + COUPON + " VALUES" + " "
								+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)");
				stat.setLong(1, coupon.getCouponId());
				stat.setString(2, coupon.getCreator());
				stat.setString(3, coupon.getTitle());
				stat.setDate(4, coupon.getStartDate());
				stat.setDate(5, coupon.getEndDate());
				stat.setInt(6, coupon.getAmount());
				stat.setString(7, coupon.getType().toString());
				stat.setString(8, coupon.getMessage());
				stat.setDouble(9, coupon.getPrice());
				stat.setString(10, coupon.getImage());

				System.out.println("New coupon created with Id = " + coupon.getCouponId());
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
	
	//updates a company
	@Override
	public void updateCoupon(Coupon coupon) throws AllExceptions {
		// checks existence
		if (getCoupon(coupon.getCouponId()) == null) {
			nonExistent(coupon.getCouponId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// update SQL
				PreparedStatement stat = con.prepareStatement(
						"UPDATE " + COUPON + " SET " + "CREATOR=?, TITLE=?, START_DATE=?, END_DATE=?"
								+ ", AMOUNT=?,"	+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE COUPON_ID="
									+ coupon.getCouponId());
				stat.setString(1, coupon.getTitle());
				stat.setDate(2, coupon.getStartDate());
				stat.setDate(3, coupon.getEndDate());
				stat.setDouble(4, coupon.getAmount());
				stat.setString(5, coupon.getType().toString());
				stat.setString(6, coupon.getMessage());
				stat.setDouble(7, coupon.getPrice());
				stat.setString(8, coupon.getImage().toString());

				stat.executeUpdate();
				System.out.println("Coupon with id = " + coupon.getCouponId() + " updated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}
	
	//deactivates a coupon
	@Override
	public void removeCoupon(Coupon coupon) throws AllExceptions {
		// checks existence
		if (getCoupon(coupon.getCouponId()) == null) {
			nonExistent(coupon.getCouponId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();

				// deactivation SQL
				Statement stat = con.createStatement();
				String sql = ("UPDATE " + COUPON + " SET ACTIVE='0' WHERE COUPON_ID=" + 
						coupon.getCouponId());
				stat.executeUpdate(sql);
				System.out.println("Coupon with ID = " + coupon.getCouponId() + " deactivated!");

				// release connection
				releaseConnection(con);

			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();

			}
		}
	}
	
	//deletes a company
	public void deleteCoupon(Coupon coupon) throws AllExceptions {
		// checks existence
		if (getCoupon(coupon.getCouponId()) == null) {
			nonExistent(coupon.getCouponId());
		} else {
			// get a connection (from pool)
			Connection con = null;

			try {
				con = getConnection();
				// delete SQL
				Statement stat = con.createStatement();
				String sql = ("DELETE FROM " + COUPON + " WHERE COUPON_ID=" + coupon.getCouponId());
				stat.executeUpdate(sql);
				System.out.println("Coupon with Id = " + coupon.getCouponId() + " deleted!");

				// release connection
				releaseConnection(con);
			} catch (SQLException e) {
				System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n\n");
				e.printStackTrace();
			}
		}
	}
	
	//returns a specific coupon by ID
	@Override
	public Coupon getCoupon(long couponId) throws AllExceptions {
		// get a connection (from pool)
		Connection con = null;
		Coupon coupon = new Coupon(couponId);

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COUPON + " WHERE COUPON_ID=" + couponId);
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from customer table
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));					
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				if (rs.getString("ACTIVE").equals(0)) {
					coupon.deactivate();
				}
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		//check if any real coupon returned
		if(coupon.getTitle() == null) return null;
		return coupon;
	}
	
	// returns all coupons sharing this name (I allow 2 companies to make coupons with same names or
	//resuse coupon names but return it as a list
	@Override
	public List<Coupon> getCoupons(String couponName) throws AllExceptions {
		// get a connection (from pool)
		Connection con = null;
		List<Coupon> list = new ArrayList<Coupon>();
		Coupon coupon;

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COUPON + " WHERE TITLE='" + couponName + "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from customer table
				coupon = new Coupon(rs.getLong("COUPON_ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				if (rs.getString("ACTIVE").equals(0)) {
					coupon.deactivate();
				}
				list.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		// check if any real coupon returned
		//if (coupon.getTitle() == null)
		//	return null;
		return list;
	}

	//returns all coupons
	@Override
	public List<Coupon> getAllCoupons() throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		List<Coupon> list = new ArrayList<Coupon>();
		
		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COUPON + " WHERE ACTIVE='1'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from coupon table
				list.add(new Coupon(rs.getLong("COUPON_ID"), rs.getString("CREATOR"), 
						rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
							rs.getInt("AMOUNT"),CouponType.valueOf(rs.getString("TYPE")),
								rs.getString("MESSAGE"), rs.getDouble("PRICE"), rs.getString("IMAGE")));
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		return list;
	}

	//returns all coupons by type
	@Override
	public List<Coupon> getCouponByType(CouponType type) throws AllExceptions {

		// get a connection (from pool)
		Connection con = null;
		List<Coupon> list = new ArrayList<Coupon>();

		try {
			con = getConnection();
			// create SQL select
			Statement stat = con.createStatement();
			String sql = ("SELECT * FROM " + COUPON + " WHERE ACTIVE='1' AND TYPE='" + type + "'");
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				// from coupon table
				list.add(new Coupon(rs.getLong("COUPON_ID"), rs.getString("CREATOR"),
							rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
								rs.getInt("AMOUNT"), type, rs.getString("MESSAGE"), rs.getDouble("PRICE"),
									rs.getString("IMAGE")));
			}
		} catch (SQLException e) {
			System.out.println("~~~~~~~~~~~~~SQL ERROR!~~~~~~~~~~~~\n");
			e.printStackTrace();
		}
		releaseConnection(con);
		return list;
	}
	
	// checks whether the coupon really exists based on ID
	private void nonExistent(long couponId) {
		String msg = "Coupon with the ID " + couponId + " doesn't exist!";
		System.out.println(msg);
	}

	// checks whether a certain coupon name already exists
	private boolean existingName(String couponName) throws AllExceptions {
		if (getAllCoupons().contains(couponName))
			return true;
		return false;
	}

}
