package com.sparta.outsourcing.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.global.dto.CommonResponseDto;
import com.sparta.outsourcing.global.jwt.JwtProvider;
import com.sparta.outsourcing.global.jwt.TokenState;
import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;
import com.sparta.outsourcing.global.jwt.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final TokenRepository tokenRepository;
  private final UserDetailsServiceImpl userDetailsService;

  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
      throws ServletException, IOException {

    String tokenValue = jwtProvider.getAccessTokenFromRequest(req);

    if (StringUtils.hasText(tokenValue)) {
      TokenState state = jwtProvider.validateToken(tokenValue);

      if (state.equals(TokenState.EXPIRED)) {
        try {
          Claims info = jwtProvider.getMemberInfoFromExpiredToken(tokenValue);
          UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(
              info.getSubject());
          RefreshTokenEntity refreshToken = tokenRepository.findByMemberId(
              userDetails.getUser().getId());

          TokenState refreshState = jwtProvider.validateToken(refreshToken.getToken());

          if(refreshState.equals(TokenState.VALID)) {
            String newAccessToken = jwtProvider.generateRefreshToken(userDetails.getUsername(), MemberRole.USER);
            res.addHeader(JwtProvider.AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY, newAccessToken);
            res.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = objectMapper.writeValueAsString(
                CommonResponseDto.ok("새로운 토큰이 발급되었습니다.", null));

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonResponse);
            return;
          } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String jsonResponse = objectMapper.writeValueAsString(
                CommonResponseDto.unauthorizedRequest("모든 토큰이 만료되었습니다."));
            tokenRepository.deleteToken(refreshToken);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonResponse);
            return;
          }
        } catch (Exception e) {
          log.error(e.getMessage());
          return;
        }
      } else if(state.equals(TokenState.INVALID)) {
        log.error("Token Error");
        return;
      }

      Claims info = jwtProvider.getMemberInfoFromToken(tokenValue);

      try {
        setAuthentication(info.getSubject());
      } catch (Exception e) {
        log.error(e.getMessage());
        return;
      }
    }

    filterChain.doFilter(req, res);
  }

  // 인증 처리
  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  // 인증 객체 생성
  private Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
