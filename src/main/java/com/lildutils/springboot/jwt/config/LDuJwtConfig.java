package com.lildutils.springboot.jwt.config;

public class LDuJwtConfig
{
	private String	authorizationHeader;
	private String	authorizationSchema;
	private String	secret;
	private String	issuer;

	public LDuJwtConfig()
	{
		super();
	}

	public String getAuthorizationHeader()
	{
		return authorizationHeader;
	}

	public void setAuthorizationHeader( String authorizationHeader )
	{
		this.authorizationHeader = authorizationHeader;
	}

	public String getAuthorizationSchema()
	{
		return authorizationSchema;
	}

	public void setAuthorizationSchema( String authorizationSchema )
	{
		this.authorizationSchema = authorizationSchema;
	}

	public String getSecret()
	{
		return secret;
	}

	public void setSecret( String secret )
	{
		this.secret = secret;
	}

	public String getIssuer()
	{
		return issuer;
	}

	public void setIssuer( String issuer )
	{
		this.issuer = issuer;
	}

}
