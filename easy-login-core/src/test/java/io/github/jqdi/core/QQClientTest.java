package io.github.jqdi.core;

import io.github.jqdi.core.repository.CacheOauthRepository;
import io.github.jqdi.core.repository.CacheTempOauthRepository;
import io.github.jqdi.core.repository.CacheVerifycodeRepository;
import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import io.github.jqdi.easylogin.core.qq.QQClient;
import io.github.jqdi.easylogin.core.qq.request.APIRequest;
import io.github.jqdi.easylogin.core.qq.request.IQQRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.OauthTempRepository;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;

public class QQClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();
		OauthTempRepository oauthTempRepository = new CacheTempOauthRepository();

		String appid = "51s3ad13sa1d";
		String appkey = "LTAIkcl1bVhsEpGf";

		IQQRequest qqRequest = new APIRequest(appid, appkey);

//		qqRequest.token("test", "test");
//		qqRequest.getOpenid("test");
//		qqRequest.getUserInfo("test", "test");

		LoginClient loginClient = new QQClient(oauthRepository, oauthTempRepository, qqRequest);

		String redirectUri = "http://open.xxx.com/openapi/callback";
		String code = "aaaaaa";
//		String userId = loginClient.login(new LoginParams.Builder().authcode(code).build());
		String userId = loginClient.login(LoginParams.builder().qq(redirectUri, code).build());
		System.out.println(userId);// 是null代表要绑定账号

		VerifycodeRepository verifycodeRepository = new CacheVerifycodeRepository();
		loginClient = new MobileCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);

		String mobile = "15288888888";
		String verifycode = "123456";
		String authcode = code;
		userId = loginClient.login(LoginParams.builder().mobileCodeBind(mobile, verifycode, authcode).build());
		System.out.println(userId);
	}
}

