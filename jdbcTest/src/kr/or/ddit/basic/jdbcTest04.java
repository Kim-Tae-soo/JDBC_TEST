package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class jdbcTest04 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      conn =
          DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "KTS98", "java");
      System.out.println("계좌 번호 추가하기");
      System.out.println();

      System.out.print("계좌번호 입력 >> ");
      String bankNo = scanner.next();
      System.out.print("은행명 입력 >> ");
      String bankName = scanner.next();
      System.out.print("예금주명 입력 >> ");
      String bankUserName = scanner.next();

      // Statement 객체를 이용한 처리
      //      String sql = "insert into bankinfo " + "(bank_no, bank_name, bank_user_name, bank_date) "
      //          + "values ('" + bankNo + "', '" + bankName + "','" + bankUserName + "', SYSDATE)";
      //
      //      System.out.println(sql);
      //
      //      stmt = conn.createStatement();
      //
      //      // 'SELECT'문을 실행할 때는 executeQuery()메소드를 사용하고,
      //      // 'INSERT', 'DELETE', 'UPDATE'문과 같이 'SELECT'문이 아닌 쿼리문을
      //      // 실행할 때는 executeUpdate() 메소드를 사용해야 함
      //      // executeUpdate() 메소드의 반환값 ==> 작업에 성공한 레코드 수
      //
      //      int cnt = stmt.executeUpdate(sql);
      //      System.out.println("반환값 : " + cnt);

      //--------------------------------------------------

      // PreparedStatement 객체를 이용하여 처리하기

      // SQL문을 작성할 때 SQL문에 데이터가 들어갈 자리를 물음표(?)로 표시해서 저장
      String sql = "insert into bankinfo " + "(bank_no, bank_name, bank_user_name, bank_date) "
          + "values (?, ?, ?, SYSDATE)";

      // PreparedStatement 객체 생성
      //        ==> 이 때 사용할 SQL문을 인수값으로 넣어줌
      pstmt = conn.prepareStatement(sql);

      // SQL문의 물음표(?) 자리에 들어가 데이터 세팅
      // 형식) pstmt.set자료형이름(물음표번호, 세팅할데이터);
      //      물음표 번호는 1부터 시작
      pstmt.setString(1, bankNo);
      pstmt.setString(2, bankName);
      pstmt.setString(3, bankUserName);

      // 데이터의 세팅이 완료되면 SQL문을 실행
      // SQL문이 'SELECT'문일 경우에는 executeQuery() 메소드
      // 'SELECT'문이 아닐 경우에는 executeUpdate() 메소드를 사용
      int cnt = pstmt.executeUpdate();
      System.out.println("반환값 : " + cnt);


    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e2) {
          // TODO: handle exception
        }
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e2) {
          // TODO: handle exception
        }
      }
      if (pstmt != null) {
        try {
          pstmt.close();
        } catch (SQLException e2) {
          // TODO: handle exception
        }
      }
    }
  }

}
