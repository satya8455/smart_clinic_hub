package com.sch.service;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;
import com.sch.entity.Token;

public interface TokenService {

	Response<?> createToken(TokenDto dto);

	Response<?> getAllToken();
	
	public Token startNextToken();

}
