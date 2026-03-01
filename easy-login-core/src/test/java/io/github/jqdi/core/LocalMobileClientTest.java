package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.mobile.LocalMobileClient;
import io.github.jqdi.easylogin.core.mobile.request.AliyunOneKeyLoginRequest;
import io.github.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

public class LocalMobileClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String accessKeyId = "aaaaa";
		String accessKeySecret = "bbbbbb";
		String endpoint = "dypnsapi.aliyuncs.com";

		ILocalMobileRequest localMobileRequest = new AliyunOneKeyLoginRequest(accessKeyId, accessKeySecret, endpoint);
		LoginClient loginClient = new LocalMobileClient(oauthRepository, localMobileRequest);

		String accessToken = "aaaaaaaaaaaaa";
		String userId = loginClient.login(LoginParams.builder().localMobile(accessToken).build());
		System.out.println(userId);
	}
}

