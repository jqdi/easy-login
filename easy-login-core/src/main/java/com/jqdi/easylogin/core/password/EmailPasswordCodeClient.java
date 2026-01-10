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
 * 邮箱+密码+验证码登录
 *
 * @author JQ棣
 */
public class EmailPasswordCodeClient implements LoginClient {
    private OauthRepository oauthRepository;
    private PasswordRepository passwordRepository;
    private VerifycodeRepository verifycodeRepository;

    public EmailPasswordCodeClient(OauthRepository oauthRepository, PasswordRepository passwordRepository, VerifycodeRepository verifycodeRepository) {
        this.oauthRepository = oauthRepository;
        this.passwordRepository = passwordRepository;
        this.verifycodeRepository = verifycodeRepository;
    }

    @Override
    public String login(LoginParams params) {
        String email = params.getMobileOrUsernameOrEncryptedData();
        String password = params.getCodeOrPasswordOrIv();
        String code = params.getAuthcode();

        if (StringUtils.isBlank(email) || StringUtils.isBlank(password) || StringUtils.isBlank(code)) {
            throw new LoginException("缺失参数");
        }

        // 核对验证码
        boolean checkVerifyCode = verifycodeRepository.checkVerifycode(email, code);
        if (!checkVerifyCode) {
            throw new LoginException("验证码错误");
        }

        String userId = oauthRepository.getUserId(IdentityType.EMAIL, email);
        if (userId == null) {
            throw new LoginException("邮箱错误");
        }

        // 核对密码
        boolean checkPassword = passwordRepository.checkPassword(userId, password);
        if (!checkPassword) {
            throw new LoginException("密码错误");
        }
        return userId;
    }
}
