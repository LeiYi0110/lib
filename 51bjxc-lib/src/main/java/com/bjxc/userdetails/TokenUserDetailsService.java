package com.bjxc.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bjxc.json.JacksonBinder;

/**
 * 通过token获取用户信息的辅助类
 * @author cras  
 */
@Service("tokenService")
public class TokenUserDetailsService {
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public UserDetails loadUserByToken(String token){
		String info = (String)redisTemplate.opsForHash().get("usertokenMap", token);
		if(StringUtils.isEmpty(info)){
			throw new UnauthorizedTokenException("token失效或不存在");
		}
		UserDetails userInfo=binder.fromJson(info, UserDetails.class);
		return userInfo;
	}

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
