package com.coupons.system;

import java.sql.SQLException;
import com.coupons.exceptions.AllExceptions;
import com.coupons.facade.*;
import com.coupons.dao.ConnectionPool;

public class CouponSystemSingleton {
	private static CouponSystemSingleton couponSystemInstance = new CouponSystemSingleton();
	private static ConnectionPool pool;
	private static String dbName = "coupons";

	//Create a singleton instance of the coupon system
	public static synchronized CouponSystemSingleton getInstance() {
		if (couponSystemInstance == null) {
			// Create a CouponSystem only once
			couponSystemInstance = new CouponSystemSingleton();
		}
		return couponSystemInstance;
	}
	

	public CouponClientFacade login(String name, String password, ClientType clientType)
			throws AllExceptions {

		CouponClientFacade facade = null;
		switch (clientType) {

		case ADMIN:
			AdminFacade adminFacade = new AdminFacade();
			facade = adminFacade.login(name, password, clientType);
			break;

		case COMPANY:
			CompanyFacade companyFacade = new CompanyFacade();
			facade = companyFacade.login(name, password, clientType);

			break;
		case CUSTOMER:
			CustomerFacade customerFacade = new CustomerFacade();
			facade = customerFacade.login(name, password, clientType);
			break;
		}

		if (null == facade) {
			throw new AllExceptions("clientType = " + clientType + "name =" + name);
		}
		return facade;

	}

	static {
		String url = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";

		String username = "root";
		String password = "1234";
		try {
			pool = new ConnectionPool(url, username, password);
		} catch (SQLException e) {
			System.out.println("Cannot create connection pool. " + "reason: " + e.getMessage());
			System.exit(1);
		}
	}

	public static ConnectionPool getConnectionPool() {
		return pool;
	}

	public void shutdown() throws SQLException {
		pool.closeAllConnections();
		System.out.println("System shutdown.");
		System.exit(0);
	}
}