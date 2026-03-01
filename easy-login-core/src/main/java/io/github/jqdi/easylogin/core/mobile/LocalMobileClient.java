package io.github.jqdi.easylogin.core.mobile;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 本地手机号一键登录
 * 
 * @author JQ棣
 */
public class LocalMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private ILocalMobileRequest localMobileRequest;
	
	public LocalMobileClient(OauthRepository oauthRepository, ILocalMobileRequest localMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.localMobileRequest = localMobileRequest;
	}
	
	@Override
	public String login(LoginParams params) {
		String accessToken = params.getMobileOrUsernameOrEncryptedData();
		
		if (StringUtils.isBlank(accessToken)) {
			throw new LoginException("缺失参数");
		}
		// 通过3大运营商获取本机手机号码，可直接使用
		String mobile = localMobileRequest.getMobile(accessToken);
		String userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		return userId;
	}
}

