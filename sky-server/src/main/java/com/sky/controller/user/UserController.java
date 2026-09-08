package com.sky.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "用户相关接口")
@RequestMapping(value = "/user/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtProperties jwtProperties;
	
    @ApiOperation("微信登录")
    @PostMapping(value = "/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
    	
    	log.info("用户传递的数据: {}", userLoginDTO);
    	User user = userService.wxLogin(userLoginDTO);
    	Map<String, Object> map = new HashMap<>();
    	map.put(JwtClaimsConstant.USER_ID, user.getId());
    	String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), map);
    	UserLoginVO userLoginVO = new UserLoginVO();
    	userLoginVO.setToken(token);
    	userLoginVO.setId(user.getId());
    	userLoginVO.setOpenid(user.getOpenid());
    	return Result.success(userLoginVO);
    }
}