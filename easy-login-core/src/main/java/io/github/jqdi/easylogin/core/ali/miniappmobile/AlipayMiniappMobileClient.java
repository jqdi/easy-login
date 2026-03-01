package io.github.jqdi.easylogin.core.ali.miniappmobile;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.ali.miniappmobile.model.AliMobileUserId;
import io.github.jqdi.easylogin.core.ali.miniappmobile.request.IAlipayMaMobileRequest;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 支付宝小程序授权登录方式二（本质是拿到手机号登录）
 * 
 * @author JQ棣
 */
public class AlipayMiniappMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IAlipayMaMobileRequest alipayMaMobileRequest;
	
	public AlipayMiniappMobileClient(OauthRepository oauthRepository, IAlipayMaMobileRequest alipayMaMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.alipayMaMobileRequest = alipayMaMobileRequest;
	}
	
	@Override
	public String login(LoginParams params) {
		String authcode = params.getAuthcode();
		
		if (StringUtils.isBlank(authcode)) {
			throw new LoginException("缺失参数");
		}

		AliMobileUserId aliMobileUserId = alipayMaMobileRequest.getPhoneNumber(authcode);

		String mobile = aliMobileUserId.getMobile();
		
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		}
		
		// 绑定用户与支付宝userId关系
		oauthRepository.bindOauth(userId, IdentityType.ALI_USERID_MINIAPP, aliMobileUserId.getUserId(), authcode);
		
		return userId;
	}
}

