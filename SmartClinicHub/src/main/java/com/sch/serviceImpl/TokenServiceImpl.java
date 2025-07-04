package com.sch.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sch.dto.Response;
import com.sch.dto.TokenDto;
import com.sch.dto.TokenResponseDto;
import com.sch.entity.Token;
import com.sch.entity.User;
import com.sch.enums.TokenStatus;
import com.sch.repository.TokenRepository;
import com.sch.repository.UserRepository;
import com.sch.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Override
	public Response<?> createToken(TokenDto dto) {
		try {
			
		Token token = new Token(null, dto.getClinicId(), dto.getDepartmentId(), null,dto.getPatientId(), null, TokenStatus.WAITING, LocalDateTime.now());
		if(dto.getDoctor() != null) {
			User doctor = userRepository.findById(dto.getDoctor()).orElse(null);
			if(doctor != null) {
				token.setDoctor(doctor);
			}else {
				return new Response<>(HttpStatus.BAD_REQUEST.value(), "No Doctor in this id ", null);
			}
		}
		
		Token savedToken = tokenRepository.save(token);
		broadcast();
		return new Response<>(HttpStatus.OK.value(), "Token Generated", "id"+savedToken.getId());
		
		
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.OK.value(), "Token Generation failed", e.getMessage());
		}
		
	}

	@Override
	public Response<?> getAllToken() {
		try {
			
			List<Token> findAll = tokenRepository.findAll();
			findAll.stream().filter(Objects::nonNull).map(t->{
				t.setTokenNumber(t.getId().intValue());
				return t;		
			}).collect(Collectors.toList());
			
			return new Response<>(HttpStatus.OK.value(), "Token retrived", findAll);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(HttpStatus.OK.value(), e.getMessage(),false);
		}
	}
	
	 private void broadcast() {
	        Optional<Token> current = tokenRepository.findFirstByStatusOrderByCreatedAtAsc(TokenStatus.CALLED);
	        List<Token> waitingList = tokenRepository.findByStatusOrderByCreatedAtAsc(TokenStatus.WAITING);

	        TokenResponseDto display = new TokenResponseDto();
	        display.setCurrentToken(current.map(Token::getId).orElse(null));
	        display.setNextToken(waitingList.isEmpty() ? null : waitingList.get(0).getId());

	        messagingTemplate.convertAndSend("/topic/tokenDisplay", display);
	    }
	 
	    public Token startNextToken() {
	        List<Token> waitingList = tokenRepository.findByStatusOrderByCreatedAtAsc(TokenStatus.WAITING);
	        waitingList.stream().findFirst().ifPresent(token -> {
	            token.setStatus(TokenStatus.CALLED);
	            tokenRepository.save(token);
	        });

	        broadcast();
	        return waitingList.get(0);
	    }
}
