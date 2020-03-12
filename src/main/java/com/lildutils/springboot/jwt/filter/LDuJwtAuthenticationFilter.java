package com.lildutils.springboot.jwt.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.lildutils.springboot.jwt.auth.LDuJwtAuthenticationToken;

public class LDuJwtAuthenticationFilter extends BasicAuthenticationFilter
{
	private final String	authorizationHeader;
	private final String	authorizationSchema;

	public LDuJwtAuthenticationFilter( AuthenticationManager authenticationManager, LDuJwtAuthenticationFilterConfig config )
	{
		super( authenticationManager );
		this.authorizationHeader = config.getAuthorizationHeader();
		this.authorizationSchema = config.getAuthorizationSchema();
	}

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
			throws IOException, ServletException
	{
		final String authorization = request.getHeader( authorizationHeader );
		if( StringUtils.isNotBlank( authorization ) && authorization.startsWith( authorizationSchema ) )
		{
			try
			{
				final String jwt = authorization.substring( authorizationSchema.length() );
				final LDuJwtAuthenticationToken authToken = (LDuJwtAuthenticationToken) getAuthenticationManager()
						.authenticate( new UsernamePasswordAuthenticationToken( null, jwt ) );
				if( authToken.isAuthenticated() )
				{
					SecurityContextHolder.getContext().setAuthentication( authToken );
					logger.info( "JWT Authentication Success! " + request.getMethod() + " " + request.getRequestURL() );
				}
			}
			catch( Exception e )
			{
				response.setStatus( HttpStatus.FORBIDDEN.value() );
				response.setCharacterEncoding( StandardCharsets.UTF_8.name() );
				SecurityContextHolder.clearContext();
				logger.info( "JWT Authentication Failed! " + request.getMethod() + " " + request.getRequestURL() );
				logger.error( "JWT Exception: " + e );
				return;
			}
		}
		filterChain.doFilter( request, response );
	}

}
