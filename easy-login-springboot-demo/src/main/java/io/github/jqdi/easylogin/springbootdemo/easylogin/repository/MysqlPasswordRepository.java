package io.github.jqdi.easylogin.springbootdemo.easylogin.repository;

import org.springframework.stereotype.Component;

import io.github.jqdi.easylogin.core.repository.PasswordRepository;

@Component
public class MysqlPasswordRepository implements PasswordRepository {

	@Override
	public boolean checkPassword(String userId, String password) {
		return true;
	}
}

