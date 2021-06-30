package Login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	private static Connection conn;

	private PreparedStatement pstmt = null;
	private CallableStatement cstmt;
	private ResultSet rs;

	// �⺻������
	public LoginDAO() {
		if (conn == null)
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "yeonho5376!");
				System.out.printf("DB ���� ����\n");
			} catch (SQLException e) {
				System.out.printf("DB ���� ����\n");
			}
	}

	public boolean checkMember(LoginDTO dto) throws ClassNotFoundException {
		String sql = "select EXISTS (select * from member where ID=\"" + dto.getId() + "\" AND Password=" + "\""
				+ dto.getPw() + "\") as success";
		System.out.println(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);

			if (rs.next() && rs.getInt(1) == 1) {
				System.out.println("�Է��Ͻ� ���̵� �����մϴ�.");
				return true;
			} else {
				System.out.println("�Է��Ͻ� ���̵� �������� �ʽ��ϴ�.");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
