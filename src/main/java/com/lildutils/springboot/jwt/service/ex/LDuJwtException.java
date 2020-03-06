package com.lildutils.springboot.jwt.service.ex;

@SuppressWarnings("serial")
public class LDuJwtException extends RuntimeException
{
	public LDuJwtException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
	{
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public LDuJwtException( String message, Throwable cause )
	{
		super( message, cause );
	}

	public LDuJwtException( String message )
	{
		super( message );
	}

	public LDuJwtException( Throwable cause )
	{
		super( cause );
	}

}
