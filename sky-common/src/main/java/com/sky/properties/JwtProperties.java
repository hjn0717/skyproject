package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
	public String getAdminSecretKey() {
		return adminSecretKey;
	}
	public void setAdminSecretKey(String adminSecretKey) {
		this.adminSecretKey = adminSecretKey;
	}
	public long getAdminTtl() {
		return adminTtl;
	}
	public void setAdminTtl(long adminTtl) {
		this.adminTtl = adminTtl;
	}
	public String getAdminTokenName() {
		return adminTokenName;
	}
	public void setAdminTokenName(String adminTokenName) {
		this.adminTokenName = adminTokenName;
	}
	public String getUserSecretKey() {
		return userSecretKey;
	}
	public void setUserSecretKey(String userSecretKey) {
		this.userSecretKey = userSecretKey;
	}
	public long getUserTtl() {
		return userTtl;
	}
	public void setUserTtl(long userTtl) {
		this.userTtl = userTtl;
	}
	public String getUserTokenName() {
		return userTokenName;
	}
	public void setUserTokenName(String userTokenName) {
		this.userTokenName = userTokenName;
	}
	@Override
	public String toString() {
		return "JwtProperties [adminSecretKey=" + adminSecretKey + ", adminTtl=" + adminTtl + ", adminTokenName="
				+ adminTokenName + ", userSecretKey=" + userSecretKey + ", userTtl=" + userTtl + ", userTokenName="
				+ userTokenName + "]";
	}
	public JwtProperties(String adminSecretKey, long adminTtl, String adminTokenName, String userSecretKey,
			long userTtl, String userTokenName) {
		super();
		this.adminSecretKey = adminSecretKey;
		this.adminTtl = adminTtl;
		this.adminTokenName = adminTokenName;
		this.userSecretKey = userSecretKey;
		this.userTtl = userTtl;
		this.userTokenName = userTokenName;
	}
	public JwtProperties() {
		super();
	}

}
