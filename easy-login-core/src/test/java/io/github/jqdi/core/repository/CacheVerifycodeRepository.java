package io.github.jqdi.core.repository;

import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;

public class CacheVerifycodeRepository implements VerifycodeRepository {

	@Override
	public boolean checkVerifycode(String identifier, String verifyCode) {
		return true;
	}

}

