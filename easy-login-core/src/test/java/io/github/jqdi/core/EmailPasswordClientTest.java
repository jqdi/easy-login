package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.core.repository.CachePasswordRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.password.EmailPasswordClient;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;

public class EmailPasswordClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		PasswordRepository passwordRepository = new CachePasswordRepository();
		LoginClient loginClient = new EmailPasswordClient(oauthRepository, passwordRepository);

		String email = "6666666@qq.com";
		String password = "aaaaaaaaaaaaa";
		String userId = loginClient.login(LoginParams.builder().emailPassword(email, password).build());
		System.out.println(userId);
	}
}

