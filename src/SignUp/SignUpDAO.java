package SignUp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpDAO
{

	private static Connection conn;

	private PreparedStatement pstmt = null;
	private CallableStatement cstmt;
	private ResultSet rs;

	// �⺻������
	public SignUpDAO()
	{
		if (conn == null)
			try
			{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "yeonho5376!");
				System.out.printf("DB ���� ����\n");
			} catch (SQLException e)
			{
				System.out.printf("DB ���� ����\n");
			}
	}

	public boolean insertMember(SignUpDTO dto) throws ClassNotFoundException
	{
		String sql = "insert into member values(?,?,?,?,?,?,?)";
		System.out.println(sql);

		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getPhone());
			pstmt.setString(5, dto.getAddress());
			pstmt.setInt(6, 0);
			pstmt.setDate(7,dto.getDate());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/** DB���� ����(�ݱ�) */
	public void dbClose()
	{

		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				System.out.println("����:ResultSet��ü close():" + e.getMessage());
			}
		}

		if (pstmt != null)
		{
			try
			{
				pstmt.close();
			} catch (SQLException e)
			{
				System.out.println("����:PreparedStatement��ü close():" + e.getMessage());
			}
		}

		if (cstmt != null)
		{
			try
			{
				cstmt.close();
			} catch (SQLException e)
			{
				System.out.println("����:CallableStatement��ü close():" + e.getMessage());
			}
		}

		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				System.out.println("����:Connection��ü close():" + e.getMessage());
			}
		}

		conn = null;
	}// dbClose()---------

}