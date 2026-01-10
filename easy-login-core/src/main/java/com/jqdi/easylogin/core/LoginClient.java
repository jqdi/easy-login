package com.jqdi.easylogin.core;

/**
 * 登录API
 *
 * @author JQ棣
 */
public interface LoginClient {
	/**
	 * 登录
	 *
	 * @param params 登录参数
	 * @return 用户ID
	 */
	String login(LoginParams params);
}
