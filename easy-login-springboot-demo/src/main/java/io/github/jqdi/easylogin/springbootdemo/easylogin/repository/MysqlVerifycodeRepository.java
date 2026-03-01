package io.github.jqdi.easylogin.springbootdemo.easylogin.repository;

import org.springframework.stereotype.Component;

import io.github.jqdi.easylogin.core.repository.VerifycodeRepository;

@Component
public class MysqlVerifycodeRepository implements VerifycodeRepository {

	@Override
	public boolean checkVerifycode(String identifier, String verifyCode) {
		return true;
	}

}

