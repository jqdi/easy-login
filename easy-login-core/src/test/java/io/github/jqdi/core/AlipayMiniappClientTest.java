package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.ali.miniapp.AlipayMiniappClient;
import io.github.jqdi.easylogin.core.ali.miniapp.request.AlipayMaRequest;
import io.github.jqdi.easylogin.core.ali.miniapp.request.IAlipayMaRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

public class AlipayMiniappClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String aesKey = "321a3s12dsa";
		String appid = "51s3ad13sa1d";
		String privateKey = "LTAIkcl1bVhsEpGf";
		String publicKey = "13sa1d3s1adsadsdsd6sa51d651";

		IAlipayMaRequest aliMaRequest = new AlipayMaRequest(aesKey, appid, privateKey, publicKey);
		LoginClient loginClient = new AlipayMiniappClient(oauthRepository, aliMaRequest);

		String encryptedData = "aaaaaaaaa";
		String authcode = "aaaaaa";

		String userId = loginClient.login(LoginParams.builder().alipayMiniapp(encryptedData, authcode).build());
		System.out.println(userId);
	}
}

