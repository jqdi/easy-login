package io.github.jqdi.easylogin.core.wx.miniapp.request;

import io.github.jqdi.easylogin.core.wx.miniapp.model.MaSession;
import io.github.jqdi.easylogin.core.wx.miniapp.model.MaSessionPhoneNumber;

/**
 * 微信小程序API对接（方式一）
 * 
 * @author JQ棣
 */
public interface IMaRequest {

	MaSession getSessionInfo(String code);

	MaSessionPhoneNumber getSessionInfoAndPhoneNumber(String encryptedData, String iv, String code);
}

