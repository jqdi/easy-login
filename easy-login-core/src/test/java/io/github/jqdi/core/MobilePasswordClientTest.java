package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.core.repository.CachePasswordRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.password.MobilePasswordClient;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;

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

