package top.yistar.wx.wxapp.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.io.Serializable;

/**
 * 
 * @author ChicUniqueKing
 *
 */

public class User implements Serializable{

	private  String id ;

	private String name ;
	
	private String username;
	
	private String password;
	
	private String phone;
	
	private String nickname;

	private String img_url;

	public User() {
	}

	public User(String id, String name, String username, String password, String phone, String nickname, String img_url) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.nickname = nickname;
		this.img_url = img_url;
	}

	public String getId() {
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

	public String getImg_url() {
		return img_url;
	}

	public void setId(String id) {
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

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				", nickname='" + nickname + '\'' +
				", img_url='" + img_url + '\'' +
				'}';
	}
}
