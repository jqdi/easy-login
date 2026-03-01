package io.github.jqdi.easylogin.core.wx.miniappmobile;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.wx.miniappmobile.request.IMaMobileRequest;

/**
 * 微信小程序登录
 * 
 * @author JQ棣
 */
public class WeixinMiniappMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IMaMobileRequest maMobileRequest;

	public WeixinMiniappMobileClient(OauthRepository oauthRepository, IMaMobileRequest maMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.maMobileRequest = maMobileRequest;
	}

	@Override
	public String login(LoginParams params) {
		String code = params.getAuthcode();
		
		if (StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		String mobile = maMobileRequest.getMobile(code);

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		}
		return userId;
	}
}

