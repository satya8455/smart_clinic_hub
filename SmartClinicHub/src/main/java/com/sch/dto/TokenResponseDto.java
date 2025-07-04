package com.sch.dto;

public class TokenResponseDto {
	
	private Long currentToken;
	
	private Long nextToken;

	public Long getCurrentToken() {
		return currentToken;
	}

	public void setCurrentToken(Long currentToken) {
		this.currentToken = currentToken;
	}

	public Long getNextToken() {
		return nextToken;
	}

	public void setNextToken(Long nextToken) {
		this.nextToken = nextToken;
	}
	
	

}
