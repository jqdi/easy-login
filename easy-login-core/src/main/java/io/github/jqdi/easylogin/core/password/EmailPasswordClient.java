package io.github.jqdi.easylogin.core.password;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 邮箱+密码登录
 * 
 * @author JQ棣
 */
public class EmailPasswordClient implements LoginClient {

	private OauthRepository oauthRepository;
	private PasswordRepository passwordRepository;

	public EmailPasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		this.oauthRepository = oauthRepository;
		this.passwordRepository = passwordRepository;
	}

	@Override
	public String login(LoginParams params) {
		String email = params.getMobileOrUsernameOrEncryptedData();
		String password = params.getCodeOrPasswordOrIv();
		
		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
			throw new LoginException("缺失参数");
		}

		String userId = oauthRepository.getUserId(IdentityType.EMAIL, email);
		if (userId == null) {
			throw new LoginException("邮箱错误");
		}
		boolean checkPassword = passwordRepository.checkPassword(userId, password);
		if (!checkPassword) {
			throw new LoginException("密码错误");
		}
		return userId;
	}
}

