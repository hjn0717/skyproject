package com.sky.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.UserNotLoginException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private WeChatProperties weChatProperties;
	@Autowired
	private UserMapper userMapper;
	
	private static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
	@Override
	public User wxLogin(UserLoginDTO userLoginDTO) {
	    Map<String, String> map = new HashMap<>();
	    map.put("appid", weChatProperties.getAppid());
	    map.put("secret", weChatProperties.getSecret());
	    map.put("grant_type", "authorization_code");
	    map.put("code", userLoginDTO.getCode());
	    String json = HttpClientUtil.doGet(WX_URL, map);
	    JSONObject jsonObject = JSONObject.parseObject(json);
	    String openId = jsonObject.getString(key: "openid");
	    if (openId == null) {
	        throw new UserNotLoginException(MessageConstant.LOGIN_FAILED);
	    }
	    User user =  userMapper.selectUserIdByOpenId(openId);
	    if(user == null){
	        // 代表此用户没有注册过
	        user = User.builder().openid(openId).createTime(LocalDateTime.now()).build();
	        // 注册用户
	        userMapper.insertUser();
	    }
	    return null;
	}
}
