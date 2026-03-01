package io.github.jqdi.easylogin.core.wx.miniapp;

import org.apache.commons.lang3.StringUtils;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.LoginParams;
import io.github.jqdi.easylogin.core.constants.IdentityType;
import io.github.jqdi.easylogin.core.exception.LoginException;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.wx.miniapp.model.MaSession;
import io.github.jqdi.easylogin.core.wx.miniapp.model.MaSessionPhoneNumber;
import io.github.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;

/**
 * 微信小程序登录
 * 
 * @author JQ棣
 */
public class WeixinMiniappClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IMaRequest maRequest;

	public WeixinMiniappClient(OauthRepository oauthRepository, IMaRequest maRequest) {
		this.oauthRepository = oauthRepository;
		this.maRequest = maRequest;
	}

	@Override
	public String login(LoginParams params) {
		String encryptedData = params.getMobileOrUsernameOrEncryptedData();
		String iv = params.getCodeOrPasswordOrIv();
		String wxcode = params.getAuthcode();
		
		if (StringUtils.isBlank(encryptedData) || StringUtils.isBlank(iv) || StringUtils.isBlank(wxcode)) {
			throw new LoginException("缺失参数");
		}

		MaSessionPhoneNumber maSessionPhoneNumber = maRequest.getSessionInfoAndPhoneNumber(encryptedData, iv, wxcode);
		MaSession maSession = maSessionPhoneNumber.getMaSession();

		String mobile = maSessionPhoneNumber.getPhoneNumber();
		
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		}

		// 绑定用户与openid,unionid关系
		String openid = maSession.getOpenid();
		oauthRepository.bindOauth(userId, IdentityType.WX_OPENID_MINIAPP, openid, wxcode);
		String unionid = maSession.getUnionid();
		if (StringUtils.isNotBlank(unionid)) {
			oauthRepository.bindOauth(userId, IdentityType.WX_UNIONID, unionid, wxcode);
		}

		return userId;
	}
}

