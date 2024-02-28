package com.sparta.outsourcing.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.domain.member.controller.dto.LoginRequestDto;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import com.sparta.outsourcing.global.jwt.JwtProvider;
import com.sparta.outsourcing.global.jwt.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtProvider jwtProvider;
  private final TokenRepository tokenRepository;
  ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFilter(JwtProvider jwtProvider, TokenRepository tokenRepository) {
    this.jwtProvider = jwtProvider;
    this.tokenRepository = tokenRepository;
    setFilterProcessesUrl("/api/members/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          LoginRequestDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getEmail(),
              requestDto.getPassword(),
              null
          )
      );
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    Member member = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

    String token = jwtProvider.generateAccessToken(member.getEmail(), member.getRole());
    String refreshToken = jwtProvider.generateRefreshToken(member.getEmail(), member.getRole());
    refreshToken = jwtProvider.substringToken(refreshToken);
    tokenRepository.register(member.getId(), refreshToken);
    response.addHeader(JwtProvider.AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY, token);
    response.setStatus(HttpServletResponse.SC_OK);

    String jsonResponse = objectMapper.writeValueAsString(
        CommonResponseDto.ok("로그인 성공하였습니다", null));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    String jsonResponse = objectMapper.writeValueAsString(
        CommonResponseDto.badRequest("로그인 실패하셨습니다."));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }
}
