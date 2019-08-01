package top.yistar.wx.wxapp.entity;

import jdk.nashorn.internal.objects.annotations.Setter;

import java.io.Serializable;

/**
 * 
 * @author ChicUniqueKing
 *
 */

public class User implements Serializable{

	private  int id ;

	private String name ;
	
	private String username;
	
	private String password;
	
	private String phone;
	
	private String nickname;

	public User() {
	}

	public User(int id, String name, String username, String password, String phone, String nickname) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
