package top.yistar.wx.wxapp.entity;

import java.io.Serializable;

/**
 * 
 * @author ChicUniqueKing
 *
 */
public class User implements Serializable{
	
	private String username;
	
	private String password;
	
	private String phone;
	
	private String nickname;

	public User(String username, String password, String phone, String nickname) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.nickname = nickname;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	
	
	

}
