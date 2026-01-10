package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CacheTempOauthRepository;
import com.jqdi.core.repository.CacheVerifycodeRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.LoginParams;
import com.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;
import com.jqdi.easylogin.core.wx.mp.WeixinAppClient;
import com.jqdi.easylogin.core.wx.mp.request.BinarywangMpRequest;
import com.jqdi.easylogin.core.wx.mp.request.IMpRequest;

public class WeixinAppClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();
		OauthTempRepository oauthTempRepository = new CacheTempOauthRepository();

		String appid = "51s3ad13sa1d";
		String secret = "LTAIkcl1bVhsEpGf";

		IMpRequest mpRequest = new BinarywangMpRequest(appid, secret);
		LoginClient loginClient = new WeixinAppClient(oauthRepository, oauthTempRepository, mpRequest);

		String wxcode = "aaaaaa";
		String userId = loginClient.login(LoginParams.builder().weixinApp(wxcode).build());
		System.out.println(userId);// 是null代表要绑定账号

		VerifycodeRepository verifycodeRepository = new CacheVerifycodeRepository();
		loginClient = new MobileCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);

		String mobile = "15288888888";
		String code = "123456";
		String authcode = wxcode;
		userId = loginClient.login(LoginParams.builder().mobileCodeBind(mobile, code, authcode).build());
		System.out.println(userId);
	}
}
