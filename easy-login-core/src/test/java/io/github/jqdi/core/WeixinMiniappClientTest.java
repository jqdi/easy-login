package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.wx.miniapp.WeixinMiniappClient;
import io.github.jqdi.easylogin.core.wx.miniapp.request.BinarywangMaRequest;
import io.github.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;

public class WeixinMiniappClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String appid = "51s3ad13sa1d";
		String secret = "LTAIkcl1bVhsEpGf";

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret);
		LoginClient loginClient = new WeixinMiniappClient(oauthRepository, maRequest);

		String encryptedData = "aaaaaaaaa";
		String iv = "aaaaa";
		String wxcode = "aaaaaa";
		String userId = loginClient.login(LoginParams.builder().weixinMiniapp(encryptedData, iv, wxcode).build());
		System.out.println(userId);
	}
}

