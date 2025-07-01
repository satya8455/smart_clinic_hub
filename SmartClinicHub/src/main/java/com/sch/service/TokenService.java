package com.sch.service;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;

public interface TokenService {

	Response<?> createToken(TokenDto tokenDto);

}
