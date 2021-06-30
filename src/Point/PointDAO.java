package Point;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PointDAO {

	private static Connection conn;

	private PreparedStatement pstmt = null;
	private CallableStatement cstmt;
	private ResultSet rs;

	// �⺻������
	public PointDAO() {
		if (conn == null)
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "yoenho5376!");
				System.out.printf("DB ���� ����\n");
			} catch (SQLException e) {
				System.out.printf("DB ���� ����\n");
			}
	}

	public String getPoint(String id, String pw) throws ClassNotFoundException {
		String sql = "select point from member where id=\'" + id + "\' AND password=\'" + pw + "\'";
		System.out.println(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String spendPoint(int beforePrice,int afterPrice) {
		return String.valueOf(beforePrice-afterPrice);
	}

	/** DB���� ����(�ݱ�) */
	public void dbClose() {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("����:ResultSet��ü close():" + e.getMessage());
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("����:PreparedStatement��ü close():" + e.getMessage());
			}
		}

		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println("����:CallableStatement��ü close():" + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("����:Connection��ü close():" + e.getMessage());
			}
		}

		conn = null;
	}// dbClose()---------

}