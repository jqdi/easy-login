package io.github.jqdi.easylogin.core.password;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 手机号+密码+验证码登录
 *
 * @author JQ棣
 */
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

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {
			throw new LoginException("手机号错误");
		}

		// 核对密码
		boolean checkPassword = passwordRepository.checkPassword(userId, password);
		if (!checkPassword) {
			throw new LoginException("密码错误");
		}
		return userId;
	}
}

