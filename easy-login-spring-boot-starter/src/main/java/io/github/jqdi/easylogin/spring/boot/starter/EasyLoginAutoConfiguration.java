package io.github.jqdi.easylogin.spring.boot.starter;

import io.github.jqdi.easylogin.core.LoginClient;
import io.github.jqdi.easylogin.core.ali.miniapp.AlipayMiniappClient;
import io.github.jqdi.easylogin.core.ali.miniapp.request.AlipayMaRequest;
import io.github.jqdi.easylogin.core.ali.miniapp.request.IAlipayMaRequest;
import io.github.jqdi.easylogin.core.ali.miniappmobile.AlipayMiniappMobileClient;
import io.github.jqdi.easylogin.core.ali.miniappmobile.request.AlipayMaMobileRequest;
import io.github.jqdi.easylogin.core.ali.miniappmobile.request.IAlipayMaMobileRequest;
import io.github.jqdi.easylogin.core.email.EmailCodeBindClient;
import io.github.jqdi.easylogin.core.email.EmailCodeClient;
import io.github.jqdi.easylogin.core.mobile.LocalMobileClient;
import io.github.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import io.github.jqdi.easylogin.core.mobile.MobileCodeClient;
import io.github.jqdi.easylogin.core.mobile.request.AliyunOneKeyLoginRequest;
import io.github.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import io.github.jqdi.easylogin.core.password.*;
import io.github.jqdi.easylogin.core.qq.QQClient;
import io.github.jqdi.easylogin.core.qq.request.APIRequest;
import io.github.jqdi.easylogin.core.qq.request.IQQRequest;
import io.github.jqdi.easylogin.core.repository.OauthRepository;
import io.github.jqdi.easylogin.core.repository.OauthTempRepository;
import io.github.jqdi.easylogin.core.repository.PasswordRepository;
import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;
import io.github.jqdi.easylogin.core.wx.miniapp.WeixinMiniappClient;
import io.github.jqdi.easylogin.core.wx.miniapp.request.BinarywangMaRequest;
import io.github.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;
import io.github.jqdi.easylogin.core.wx.miniappmobile.WeixinMiniappMobileClient;
import io.github.jqdi.easylogin.core.wx.miniappmobile.request.BinarywangMaMobileRequest;
import io.github.jqdi.easylogin.core.wx.miniappmobile.request.IMaMobileRequest;
import io.github.jqdi.easylogin.core.wx.mp.WeixinAppClient;
import io.github.jqdi.easylogin.core.wx.mp.WeixinMpClient;
import io.github.jqdi.easylogin.core.wx.mp.request.BinarywangMpRequest;
import io.github.jqdi.easylogin.core.wx.mp.request.IMpRequest;
import io.github.jqdi.easylogin.spring.boot.starter.properties.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(OauthRepository.class)
@EnableConfigurationProperties({ LocalMobileProperties.class, WeixinAppProperties.class, WeixinMiniappProperties.class,
		WeixinMiniappMobileProperties.class, WeixinMpProperties.class, AlipayMiniappProperties.class,
		AlipayMiniappMobileProperties.class, QQProperties.class })
public class EasyLoginAutoConfiguration {

	@Bean(LoginType.USERNAME_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.USERNAME_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient usernamePasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new UsernamePasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.MOBILE_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient mobilePasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new MobilePasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.EMAIL_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient emailPasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new EmailPasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.USERNAME_PASSWORD_CODE)
	@ConditionalOnMissingBean(name = LoginType.USERNAME_PASSWORD_CODE)
	@ConditionalOnBean({PasswordRepository.class, VerifycodeRepository.class})
	LoginClient usernamePasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository, VerifycodeRepository verifycodeRepository) {
		return new UsernamePasswordCodeClient(oauthRepository, passwordRepository, verifycodeRepository);
	}

	@Bean(LoginType.MOBILE_PASSWORD_CODE)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_PASSWORD_CODE)
	@ConditionalOnBean({PasswordRepository.class, VerifycodeRepository.class})
	LoginClient mobilePasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository, VerifycodeRepository verifycodeRepository) {
		return new MobilePasswordCodeClient(oauthRepository, passwordRepository, verifycodeRepository);
	}

	@Bean(LoginType.EMAIL_PASSWORD_CODE)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_PASSWORD_CODE)
	@ConditionalOnBean({PasswordRepository.class, VerifycodeRepository.class})
	LoginClient emailPasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository, VerifycodeRepository verifycodeRepository) {
		return new EmailPasswordCodeClient(oauthRepository, passwordRepository, verifycodeRepository);
	}

	@Bean(LoginType.LOCAL_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.LOCAL_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.localMobile", name = "accessKeyId")
	LoginClient localMobileClient(OauthRepository oauthRepository, LocalMobileProperties properties) {
		String accessKeyId = properties.getAccessKeyId();
		String accessKeySecret = properties.getAccessKeySecret();
		String endpoint = properties.getEndpoint();
		ILocalMobileRequest localMobileRequest = new AliyunOneKeyLoginRequest(accessKeyId, accessKeySecret, endpoint);
		return new LocalMobileClient(oauthRepository, localMobileRequest);
	}

	@Bean(LoginType.MOBILE_CODE)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_CODE)
	@ConditionalOnBean(VerifycodeRepository.class)
	LoginClient mobileCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		return new MobileCodeClient(oauthRepository, verifycodeRepository);
	}

	@Bean(LoginType.MOBILE_CODE_BIND)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_CODE_BIND)
	@ConditionalOnBean({ OauthTempRepository.class, VerifycodeRepository.class })
	LoginClient mobileCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		return new MobileCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);
	}

	@Bean(LoginType.EMAIL_CODE)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_CODE)
	@ConditionalOnBean(VerifycodeRepository.class)
	LoginClient emailCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		return new EmailCodeClient(oauthRepository, verifycodeRepository);
	}

	@Bean(LoginType.EMAIL_CODE_BIND)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_CODE_BIND)
	@ConditionalOnBean({ OauthTempRepository.class, VerifycodeRepository.class })
	LoginClient emailCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		return new EmailCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);
	}

	@Bean(LoginType.WEIXIN_APP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_APP)
	@ConditionalOnBean(OauthTempRepository.class)
	@ConditionalOnProperty(prefix = "easylogin.weixinApp", name = "appid")
	LoginClient weixinAppClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			WeixinAppProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();

		IMpRequest mpRequest = new BinarywangMpRequest(appid, secret);
		return new WeixinAppClient(oauthRepository, oauthTempRepository, mpRequest);
	}

	@Bean(LoginType.WEIXIN_MINIAPP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MINIAPP)
	@ConditionalOnProperty(prefix = "easylogin.weixinMiniapp", name = "appid")
	LoginClient weixinMiniappClient(OauthRepository oauthRepository, WeixinMiniappProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret);
		return new WeixinMiniappClient(oauthRepository, maRequest);
	}

	@Bean(LoginType.WEIXIN_MINIAPP_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MINIAPP_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.weixinMiniappMobile", name = "appid")
	LoginClient weixinMiniappMobileClient(OauthRepository oauthRepository, WeixinMiniappMobileProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();

		IMaMobileRequest maRequest = new BinarywangMaMobileRequest(appid, secret);
		return new WeixinMiniappMobileClient(oauthRepository, maRequest);
	}

	@Bean(LoginType.WEIXIN_MP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MP)
	@ConditionalOnBean(OauthTempRepository.class)
	@ConditionalOnProperty(prefix = "easylogin.weixinMp", name = "appid")
	LoginClient weixinMpClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			WeixinMpProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();

		IMpRequest mpRequest = new BinarywangMpRequest(appid, secret);
		return new WeixinMpClient(oauthRepository, oauthTempRepository, mpRequest);
	}

	@Bean(LoginType.ALIPAY_MINIAPP)
	@ConditionalOnMissingBean(name = LoginType.ALIPAY_MINIAPP)
	@ConditionalOnProperty(prefix = "easylogin.alipayMiniapp", name = "appid")
	LoginClient alipayMiniappClient(OauthRepository oauthRepository, AlipayMiniappProperties properties) {
		String aesKey = properties.getAesKey();
		String appid = properties.getAppid();
		String privateKey = properties.getPrivateKey();
		String publicKey = properties.getPublicKey();

		IAlipayMaRequest alipayMaRequest = new AlipayMaRequest(aesKey, appid, privateKey, publicKey);
		return new AlipayMiniappClient(oauthRepository, alipayMaRequest);
	}

	@Bean(LoginType.ALIPAY_MINIAPP_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.ALIPAY_MINIAPP_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.alipayMiniappMobile", name = "appid")
	LoginClient alipayMiniappMobileClient(OauthRepository oauthRepository, AlipayMiniappMobileProperties properties) {
		String appid = properties.getAppid();
		String privateKey = properties.getPrivateKey();
		String publicKey = properties.getPublicKey();

		IAlipayMaMobileRequest alipayMaMobileRequest = new AlipayMaMobileRequest(appid, privateKey, publicKey);
		return new AlipayMiniappMobileClient(oauthRepository, alipayMaMobileRequest);
	}

	@Bean(LoginType.QQ)
	@ConditionalOnMissingBean(name = LoginType.QQ)
	@ConditionalOnProperty(prefix = "easylogin.qq", name = "appid")
	LoginClient qqClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			QQProperties properties) {
		String appid = properties.getAppid();
		String appkey = properties.getAppkey();

		IQQRequest qqRequest = new APIRequest(appid, appkey);
		return new QQClient(oauthRepository, oauthTempRepository, qqRequest);
	}

}

