package com.platform.authentication.authorization;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.platform.authentication.model.ManagementLoginDTO;
import com.platform.authentication.token.RedisTokenUtil;

@Component
public class PlatformAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private PlatformUserDetailsService userDetailsService;
	
    @Autowired
    private PlatformPasswordEncoder encoder;
    
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String member_username = (String) authentication.getPrincipal();
		String member_pwd = (String) authentication.getCredentials();
		
		ManagementLoginDTO member = userDetailsService.loadUserByUsername(member_username);
        // bcrypt의 비밀번호 체크 메소드
		if (!encoder.matches(member_pwd, member.getPwd())) {
			throw new BadCredentialsException(member_username);
		}
 
		if (StringUtils.equals(member.getUserLoginNotAllowYn(), "Y")) {
			throw new DisabledException(member_username);
		}
        
		List<PlatformGrantedAuthority> roles = member.getAuthorities();
		// 스프링 시큐리티 내부 클래스로 인증 토큰 생성
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(member_username, member_pwd, roles);
		redisTokenUtil.authorization(member);
		return result;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	
//	private boolean TokenValidation() {
//		boolean isAccess = false;
//		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.ACCESS_TOKEN_NAME);
//
//        String username = null;
//        String jwt = null;
//        String refreshJwt = null;
//        String refreshUname = null;
//
//        try{
//            if(jwtToken != null){
//                jwt = jwtToken.getValue();
//                username = jwtUtil.getUsername(jwt);
//            }
//            if(username!=null){
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                if(jwtUtil.validateToken(jwt,userDetails)){
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }
//        }catch (ExpiredJwtException e){
//            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,JwtUtil.REFRESH_TOKEN_NAME);
//            if(refreshToken!=null){
//                refreshJwt = refreshToken.getValue();
//            }
//        }catch(Exception e){
//
//        }
//
//        try{
//            if(refreshJwt != null){
//                refreshUname = redisUtil.getData(refreshJwt);
//
//                if(refreshUname.equals(jwtUtil.getUsername(refreshJwt))) {
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUname);
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    ManagementLoginDTO member = new ManagementLoginDTO();
//                    member.setUserNm(refreshUname);
//                    String newToken = jwtUtil.generateToken(member);
//
//                    Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME,newToken);
//                    httpServletResponse.addCookie(newAccessToken);
//                }
//            }
//        }catch(ExpiredJwtException e){
//
//        }
//	}
}
