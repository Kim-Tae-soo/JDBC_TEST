package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
    문제) LPROD_ID값을 2개 입력 받아 두 값 중 작은 값부터 큰 값 사이의 자료들을 출력하시오
*/

public class jdbcTest03 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    System.out.print("LPROD_ID 두개 입력 하십시오. >> ");
    int lprod_id1 = scanner.nextInt();
    int lprod_id2 = scanner.nextInt();
    System.out.println();


    int max = lprod_id1 > lprod_id2 ? lprod_id1 : lprod_id2;
    int min = lprod_id1 > lprod_id2 ? lprod_id2 : lprod_id1;

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");

      conn =
          DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "KTS98", "java");



      String sql = "";

      // 작은 값과 큰 값을 포함
      //      if (lprod_id1 < lprod_id2) {
      //        sql = "SELECT * FROM LPROD WHERE LPROD_ID BETWEEN " + Integer.toString(lprod_id1) + " AND "
      //            + Integer.toString(lprod_id2);
      //      } else {
      //        sql = "SELECT * FROM LPROD WHERE LPROD_ID BETWEEN " + Integer.toString(lprod_id2) + " AND "
      //            + Integer.toString(lprod_id1);
      //      }

      // 작은 값과 큰 값 포함 x
      //      if (lprod_id1 < lprod_id2) {
      //        sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + Integer.toString(lprod_id1)
      //            + " AND LPROD_ID <" + Integer.toString(lprod_id2);
      //      } else {
      //        sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + Integer.toString(lprod_id2)
      //            + " AND LPROD_ID <" + Integer.toString(lprod_id1);
      //      }
      //
      //      sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + Integer.toString(min) + " AND LPROD_ID <"
      //          + Integer.toString(max);
      //
      //      stmt = conn.createStatement();
      //
      //      rs = stmt.executeQuery(sql);
      //      System.out.println("== 쿼리문 처리 결과 ==");

      // ------------------------------------------------------------
      // PreparedStatement 객체 이용하여 처리
      //      sql = "SELECT * FROM LPROD WHERE LPROD_ID > ? AND LPROD_ID < ?";
      sql = "SELECT * FROM LPROD WHERE LPROD_ID BETWEEN ? AND ?";

      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, min);
      pstmt.setInt(2, max);

      rs = pstmt.executeQuery();
      //--------------------------------------------------------------

      System.out.println("== 결과출력 ==");

      while (rs.next()) {
        System.out.println("LPROD_ID : " + rs.getInt(1));
        System.out.println("LPROD_GU : " + rs.getString(2));
        System.out.println("LPROD_NAME : " + rs.getString(3));
        System.out.println("--------------------------------------------------");
      }
      scanner.close();

    } catch (SQLException e) {
      // TODO: handle exception
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e2) {
          // TODO: handle exception
          e2.printStackTrace();
        }
      }
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e2) {
          // TODO: handle exception
          e2.printStackTrace();
        }
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e2) {
          // TODO: handle exception
          e2.printStackTrace();
        }
      }
    }

  }

}
