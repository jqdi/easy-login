package com.jqdi.easylogin.core.password;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.LoginParams;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.PasswordRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户名+密码+验证码登录
 *
 * @author JQ棣
 */
public class UsernamePasswordCodeClient implements LoginClient {
	private OauthRepository oauthRepository;
	private PasswordRepository passwordRepository;
	private VerifycodeRepository verifycodeRepository;

	public UsernamePasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository,
                                      VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.passwordRepository = passwordRepository;
		this.verifycodeRepository = verifycodeRepository;
	}

	@Override
	public String login(LoginParams params) {
		String username = params.getMobileOrUsernameOrEncryptedData();
		String password = params.getCodeOrPasswordOrIv();
		String code = params.getAuthcode();

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(username, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		String userId = oauthRepository.getUserId(IdentityType.USERNAME, username);
		if (userId == null) {
			throw new LoginException("用户名错误");
		}

		// 核对密码
		boolean checkPassword = passwordRepository.checkPassword(userId, password);
		if (!checkPassword) {
			throw new LoginException("密码错误");
		}
		return userId;
	}
}
