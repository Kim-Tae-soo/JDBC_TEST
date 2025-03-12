package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

// JDBC 드라이버를 로딩하고 Connection 객체를 생성하는 메서드로 구성된 class만들기
// ResourceBundle 객체 이용하기
public class DBUtil3 {
	private static final Logger logger = Logger.getLogger(DBUtil3.class);
	
	
	private static ResourceBundle bundle = null;
	static {
		bundle = ResourceBundle.getBundle("kr.or.ddit.config.db");
		logger.info("ResourceBundle 객체 생성 완료 - db.properties파일 읽기 성공");
		
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(bundle.getString("driver"));
			logger.trace("DB 드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			//System.out.println("드라이버 로딩 실패");
			logger.error("DB 드라이버 로딩 실패", e);
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(bundle.getString("url"),
											   bundle.getString("user"),
											   bundle.getString("pass"));
			logger.debug("DB 접속 완료 - Connection 객체 생성 완료");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1",
//					"KTS98", 
//					"java");

		} catch (SQLException e) {
			//System.out.println("DB 연결 실패");
			logger.error("DB 접속 실패 - Connection 객체 생성 실패",e);
			e.printStackTrace();
		}
		
		return conn;
	}

}
