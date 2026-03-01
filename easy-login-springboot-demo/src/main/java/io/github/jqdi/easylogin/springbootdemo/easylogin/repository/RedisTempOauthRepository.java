package io.github.jqdi.easylogin.springbootdemo.easylogin.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.jqdi.easylogin.core.model.BindAuthCode;
import io.github.jqdi.easylogin.core.repository.OauthTempRepository;

@Component
public class RedisTempOauthRepository implements OauthTempRepository {
	Map<String, BindAuthCode> cache = new HashMap<>();

	@Override
	public void saveBindAuthCode(String authcode, BindAuthCode bindAuthCode) {
		cache.put(authcode, bindAuthCode);
	}

	@Override
	public BindAuthCode getBindAuthCode(String authcode) {
		return cache.get(authcode);
	}

}

