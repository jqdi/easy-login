package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CachePasswordRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.LoginParams;
import com.jqdi.easylogin.core.password.MobilePasswordClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.PasswordRepository;

public class MobilePasswordClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		PasswordRepository passwordRepository = new CachePasswordRepository();
		LoginClient loginClient = new MobilePasswordClient(oauthRepository, passwordRepository);

		String mobile = "18666666666";
		String password = "aaaaaaaaaaaaa";
		String userId = loginClient.login(LoginParams.builder().mobilePassword(mobile, password).build());
		System.out.println(userId);
	}
}
