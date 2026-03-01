package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.ali.miniappmobile.AlipayMiniappMobileClient;
import io.github.jqdi.easylogin.core.ali.miniappmobile.request.AlipayMaMobileRequest;
import io.github.jqdi.easylogin.core.ali.miniappmobile.request.IAlipayMaMobileRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

public class AlipayMiniappMobileClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String appid = "51s3ad13sa1d";
		String privateKey = "LTAIkcl1bVhsEpGf";
		String publicKey = "13sa1d3s1adsadsdsd6sa51d651";

		IAlipayMaMobileRequest alipayMaMobileRequest = new AlipayMaMobileRequest(appid, privateKey, publicKey);
		LoginClient loginClient = new AlipayMiniappMobileClient(oauthRepository, alipayMaMobileRequest);

		String authcode = "aaaaaa";

		String userId = loginClient.login(LoginParams.builder().alipayMiniappMobile(authcode).build());
		System.out.println(userId);
	}
}

