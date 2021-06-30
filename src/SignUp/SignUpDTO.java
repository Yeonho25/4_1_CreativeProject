package SignUp;

import java.sql.Date;

public class SignUpDTO
{
	private String id;
	private String password;
	private String name;
	private String phone;
	private String address;
	private int point;
	private Date date;

	public SignUpDTO(String id, String password, String phone, String name, String address)
	{
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.address = address;
		date = new java.sql.Date(System.currentTimeMillis());
	}

	public String getId()
	{
		return id;
	}

	public String getPassword()
	{
		return password;
	}

	public String getName()
	{
		return name;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getAddress()
	{
		return address;
	}

	public int getPoint()
	{
		return point;
	}

	public Date getDate()
	{
		return date;
	}
}
