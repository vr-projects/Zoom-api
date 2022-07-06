package com.userOnboard;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.userOnboard.Util.JWTUtil;
import com.userOnboard.model.AuthenticationRequest;
import com.userOnboard.model.AuthenticationResponse;
import com.userOnboard.service.impl.MyUserDetailsService;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
public class AuthenticationController {
	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	JWTUtil jwtUtil;

	@GetMapping("/hello")
	public String Hello() {
		return "Hello World";
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));  

		} catch (Exception ex) {
			if (ex.getMessage().contains("io.jsonwebtoken.ExpiredJwtException")) {
				// Refresh Token
				//refreshtoken(authenticationRequest)
				// try again with refresh token
				//response = getData();
				throw new Exception("Incorrect Username or password",ex); 
			}

		}
		final UserDetails userdetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwtString = jwtUtil.generateToken(userdetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwtString));
	}
		@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
			// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}


	  public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims
	  claims) { Map<String, Object> expectedMap = new HashMap<String, Object>();
	  for (java.util.Map.Entry<String, Object> entry : claims.entrySet()) {
	  expectedMap.put(entry.getKey(), entry.getValue()); } return expectedMap; }

}
