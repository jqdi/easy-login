package io.github.jqdi.easylogin.core.wx.mp.request;

import io.github.jqdi.easylogin.core.wx.mp.model.MpAccessToken;
import io.github.jqdi.easylogin.core.wx.mp.model.MpUserInfo;

/**
 * 微信公众号API对接
 * 
 * @author JQ棣
 */
public interface IMpRequest {

	MpAccessToken getAccessToken(String code);

	MpUserInfo getUserinfo(String accessToken, String openid);
}

