package top.yistar.wx.wxapp.wxcontroller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.yistar.wx.wxapp.entity.ResponsePlafe;
import top.yistar.wx.wxapp.entity.User;

/**
 * 
 * @author ChicUniqueKing
 *	 user login
 */
@RestController
public class UserLoginController {
	
	
	@RequestMapping(value="/login")
	public ResponsePlafe login(@RequestBody User user){
		
		if(user.getUsername().equals("chic")&&user.getPassword().equals("88019522")) {
			return new ResponsePlafe(1,"longin success",null);
		}
		
		return new ResponsePlafe(2,"longin failed",null);
	} 

}
