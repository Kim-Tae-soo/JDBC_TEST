package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. DB 연결
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "username", "password");

            // 3. LPROD_ID 값 계산
            String sql = "SELECT MAX(LPROD_ID) AS maxId FROM LPROD";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int newId = 0;
            if (rs.next()) {
                newId = rs.getInt("maxId") + 1;
            }

            // 4. LPROD_GU와 LPROD_NAME 입력 받기
            String lprodGu = "";
            String lprodName = "";
            boolean isExist = true;
            while (isExist) {
                System.out.print("LPROD_GU 입력: ");
                lprodGu = scanner.nextLine();

                // 5. LPROD_GU 존재 여부 확인
                sql = "SELECT COUNT(*) AS cnt FROM LPROD WHERE LPROD_GU = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, lprodGu);
                rs = pstmt.executeQuery();

                if (rs.next() && rs.getInt("cnt") == 0) {
                    isExist = false;
                } else {
                    System.out.println("이미 등록된 LPROD_GU입니다. 다시 입력하세요.");
                }
            }
            System.out.print("LPROD_NAME 입력: ");
            lprodName = scanner.nextLine();

            // 6. 새로운 레코드 추가
            sql = "INSERT INTO LPROD (LPROD_ID, LPROD_GU, LPROD_NAME) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newId);
            pstmt.setString(2, lprodGu);
            pstmt.setString(3, lprodName);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("데이터 추가 성공!");
            } else {
                System.out.println("데이터 추가 실패!");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 반납
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
            scanner.close();
        }
    }
}
