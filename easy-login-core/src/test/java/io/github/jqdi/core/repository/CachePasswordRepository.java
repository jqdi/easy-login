package io.github.jqdi.core.repository;

import io.github.jqdi.easylogin.core.repository.PasswordRepository;

public class CachePasswordRepository implements PasswordRepository {

	@Override
	public boolean checkPassword(String userId, String password) {
		return true;
	}

}

