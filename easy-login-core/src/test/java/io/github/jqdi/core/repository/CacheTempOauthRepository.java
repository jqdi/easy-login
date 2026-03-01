package io.github.jqdi.core.repository;

import java.util.HashMap;
import java.util.Map;

import io.github.jqdi.easylogin.core.model.BindAuthCode;
import io.github.jqdi.easylogin.core.repository.OauthTempRepository;

public class CacheTempOauthRepository implements OauthTempRepository {
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

