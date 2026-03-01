package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.core.repository.CacheVerifycodeRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.mobile.MobileCodeClient;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;

public class MobileCodeClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		VerifycodeRepository verifycodeRepository = new CacheVerifycodeRepository();
		LoginClient loginClient = new MobileCodeClient(oauthRepository, verifycodeRepository);

		String mobile = "15288888888";
		String code = "123456";
		String userId = loginClient.login(LoginParams.builder().mobileCode(mobile, code).build());
		System.out.println(userId);

		mobile = "15288888889";
		code = "123456";
		userId = loginClient.login(LoginParams.builder().mobileCode(mobile, code).build());
		System.out.println(userId);
	}
}

