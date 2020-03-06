package com.lildutils.springboot.jwt.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lildutils.springboot.jwt.config.LDuJwtConfig;
import com.lildutils.springboot.jwt.service.LDuJwtService;
import com.lildutils.springboot.jwt.service.ex.LDuJwtException;

@Service
public class LDuJwtServiceImpl implements LDuJwtService
{
	private final Algorithm	algorithm;
	private final String	issuer;

	public LDuJwtServiceImpl( LDuJwtConfig config )
	{
		super();
		this.algorithm = Algorithm.HMAC256( config.getSecret() );
		this.issuer = config.getIssuer();
	}

	@Override
	public String pack( String subject, Map<String, String> claims, long expirationTime )
	{
		try
		{
			final LocalDateTime now = LocalDateTime.now();

			final String jwtId = String.valueOf( (new Date()).getTime() + (Math.random() * 100) );

			final Date createdAt = Date.from( now.atZone( ZoneId.systemDefault() ).toInstant() );
			final Date expiration = Date
					.from( now.plusSeconds( expirationTime ).atZone( ZoneId.systemDefault() ).toInstant() );

			final Builder jwtBuilder = JWT.create().withJWTId( jwtId ).withSubject( subject ).withIssuedAt( createdAt )
					.withExpiresAt( expiration ).withNotBefore( createdAt ).withAudience( "JWT" ).withIssuer( issuer );

			for( Entry<String, String> entry : claims.entrySet() )
			{
				jwtBuilder.withClaim( entry.getKey(), entry.getValue() );
			}

			return jwtBuilder.sign( algorithm );
		}
		catch( Exception e )
		{
			throw new LDuJwtException( e.getMessage() );
		}
	}

	@Override
	public DecodedJWT unpack( String jwt )
	{
		try
		{
			return JWT.decode( jwt );
		}
		catch( Exception e )
		{
			throw new LDuJwtException( e.getMessage() );
		}
	}

}
