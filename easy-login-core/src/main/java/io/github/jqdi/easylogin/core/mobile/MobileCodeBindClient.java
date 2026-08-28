package io.github.jqdi.easylogin.core.mobile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.model.BindAuthCode;
import io.github.jqdi.easylogin.core.model.BindAuthCode.BindUserOauth;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.OauthTempRepository;

/**
 * 手机号+验证码登录（并绑定第三方登录信息）
 * 
 * <pre>
 * 使用场景：微信APP、微信公众号登录过程中需要绑定手机号，以手机号作为系统用户必须的登录方式（如果是以邮箱作为必须的登录方式，逻辑类似）
 * </pre>
 * 
 * @author JQ棣
 */
public class MobileCodeBindClient implements LoginClient {
	private OauthRepository oauthRepository;
	private OauthTempRepository oauthTempRepository;
	private VerifycodeRepository verifycodeRepository;

	public MobileCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.oauthTempRepository = oauthTempRepository;
		this.verifycodeRepository = verifycodeRepository;
	}

	@Override
	public String login(LoginParams params) {
		String mobile = params.getMobileOrUsernameOrEncryptedData();
		String code = params.getCodeOrPasswordOrIv();
		String bindCode = params.getAuthcode();
		
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(mobile, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);

		BindAuthCode mobileBindAuthCode = oauthTempRepository.getBindAuthCode(bindCode);

		if (userId == null) {// 账号不存在
			// 创建新用户
            Map<String, String> attachDataMap =
                Optional.ofNullable(mobileBindAuthCode).map(BindAuthCode::getAttachDataMap).orElse(Collections.emptyMap());
            userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, attachDataMap);
		}

		// 绑定authcode
		if (mobileBindAuthCode != null) {
			List<BindUserOauth> binds = mobileBindAuthCode.getBinds();
			for (BindUserOauth bind : binds) {
				String identifier = bind.getIdentifier();
				if (StringUtils.isNotBlank(identifier)) {
					oauthRepository.bindOauth(userId, bind.getIdentityType(), identifier, bindCode);
				}
			}
		}
		return userId;
	}
}

