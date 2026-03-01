package io.github.jqdi.easylogin.springbootdemo.easylogin.extend;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;

@Component("mobilePasswordCode")
public class MobilePasswordCodeClient implements LoginClient {
	private OauthRepository oauthRepository;
	private PasswordRepository passwordRepository;
	private VerifycodeRepository verifycodeRepository;

	public MobilePasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository,
			VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.passwordRepository = passwordRepository;
		this.verifycodeRepository = verifycodeRepository;
	}

	@Override
	public String login(LoginParams params) {
		String mobile = params.getMobileOrUsernameOrEncryptedData();
		String password = params.getCodeOrPasswordOrIv();
		String code = params.getAuthcode();
		
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}
		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(mobile, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		// 核对密码
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		boolean checkPassword = passwordRepository.checkPassword(userId, password);
		if (!checkPassword) {
			throw new LoginException("密码错误");
		}

		return userId;
	}
}

