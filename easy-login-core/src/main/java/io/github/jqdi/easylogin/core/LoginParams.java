package io.github.jqdi.easylogin.core;

/**
 * 登录参数类，使用Builder模式构建
 */
public class LoginParams {
    private final String mobileOrUsernameOrEncryptedData;
    private final String codeOrPasswordOrIv;
    private final String authcode;

    private LoginParams(Builder builder) {
        this.mobileOrUsernameOrEncryptedData = builder.mobileOrUsernameOrEncryptedData;
        this.codeOrPasswordOrIv = builder.codeOrPasswordOrIv;
        this.authcode = builder.authcode;
    }

    public String getMobileOrUsernameOrEncryptedData() {
        return mobileOrUsernameOrEncryptedData;
    }

    public String getCodeOrPasswordOrIv() {
        return codeOrPasswordOrIv;
    }

    public String getAuthcode() {
        return authcode;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder类
     */
    public static class Builder {
        /**
         * 用户名|手机号|加密数据
         */
        private String mobileOrUsernameOrEncryptedData;
        /**
         * 验证码|密码|解密偏移量
         */
        private String codeOrPasswordOrIv;
        /**
         * 授权code
         */
        private String authcode;

        public Builder custom(String mobileOrUsernameOrEncryptedData, String codeOrPasswordOrIv, String authcode) {
            this.mobileOrUsernameOrEncryptedData = mobileOrUsernameOrEncryptedData;
            this.codeOrPasswordOrIv = codeOrPasswordOrIv;
            this.authcode = authcode;
            return this;
        }

        public Builder usernamePassword(String username, String password) {
            this.mobileOrUsernameOrEncryptedData = username;
            this.codeOrPasswordOrIv = password;
            return this;
        }

        public Builder mobilePassword(String mobile, String password) {
            this.mobileOrUsernameOrEncryptedData = mobile;
            this.codeOrPasswordOrIv = password;
            return this;
        }

        public Builder emailPassword(String email, String password) {
            this.mobileOrUsernameOrEncryptedData = email;
            this.codeOrPasswordOrIv = password;
            return this;
        }

        public Builder usernamePasswordCode(String username, String password, String code) {
            this.mobileOrUsernameOrEncryptedData = username;
            this.codeOrPasswordOrIv = password;
            this.authcode = code;
            return this;
        }

        public Builder mobilePasswordCode(String mobile, String password, String code) {
            this.mobileOrUsernameOrEncryptedData = mobile;
            this.codeOrPasswordOrIv = password;
            this.authcode = code;
            return this;
        }

        public Builder emailPasswordCode(String email, String password, String code) {
            this.mobileOrUsernameOrEncryptedData = email;
            this.codeOrPasswordOrIv = password;
            this.authcode = code;
            return this;
        }

        public Builder mobileCode(String mobile, String code) {
            this.mobileOrUsernameOrEncryptedData = mobile;
            this.codeOrPasswordOrIv = code;
            return this;
        }

        public Builder mobileCodeBind(String mobile, String code, String bindCode) {
            this.mobileOrUsernameOrEncryptedData = mobile;
            this.codeOrPasswordOrIv = code;
            this.authcode = bindCode;
            return this;
        }

        public Builder emailCode(String email, String code) {
            this.mobileOrUsernameOrEncryptedData = email;
            this.codeOrPasswordOrIv = code;
            return this;
        }

        public Builder emailCodeBind(String email, String code, String bindCode) {
            this.mobileOrUsernameOrEncryptedData = email;
            this.codeOrPasswordOrIv = code;
            this.authcode = bindCode;
            return this;
        }

        public Builder localMobile(String accessToken) {
            this.mobileOrUsernameOrEncryptedData = accessToken;
            return this;
        }

        public Builder weixinMiniapp(String encryptedData, String iv, String wxcode) {
            this.mobileOrUsernameOrEncryptedData = encryptedData;
            this.codeOrPasswordOrIv = iv;
            this.authcode = wxcode;
            return this;
        }

        public Builder weixinMiniappMobile(String code) {
            this.authcode = code;
            return this;
        }

        public Builder weixinApp(String wxcode) {
            this.authcode = wxcode;
            return this;
        }

        public Builder weixinMp(String wxcode) {
            this.authcode = wxcode;
            return this;
        }

        public Builder alipayMiniapp(String encryptedData, String authcode) {
            this.mobileOrUsernameOrEncryptedData = encryptedData;
            this.authcode = authcode;
            return this;
        }

        public Builder alipayMiniappMobile(String authcode) {
            this.authcode = authcode;
            return this;
        }

        public Builder qq(String redirectUri, String code) {
            this.codeOrPasswordOrIv = redirectUri;
            this.authcode = code;
            return this;
        }

        public LoginParams build() {
            return new LoginParams(this);
        }
    }
}

