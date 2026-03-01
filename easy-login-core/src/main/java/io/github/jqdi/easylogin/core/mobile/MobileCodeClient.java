package io.github.jqdi.easylogin.core.mobile;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 手机号+验证码登录
 * 
 * @author JQ棣
 */
public class MobileCodeClient implements LoginClient {
	private OauthRepository oauthRepository;
	private VerifycodeRepository verifycodeRepository;

	public MobileCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.verifycodeRepository = verifycodeRepository;
	}
	
	@Override
	public String login(LoginParams params) {
		String mobile = params.getMobileOrUsernameOrEncryptedData();
		String code = params.getCodeOrPasswordOrIv();
		
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}
		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(mobile, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		}
		
		return userId;
	}
}

