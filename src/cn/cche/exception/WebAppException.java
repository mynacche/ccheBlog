package cn.cche.exception;

public class WebAppException extends Exception {

	public WebAppException() {

		super();
	}

	public WebAppException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {

		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WebAppException(String message, Throwable cause) {

		super(message, cause);
	}

	public WebAppException(String message) {

		super(message);
	}

	public WebAppException(Throwable cause) {

		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3439042893876249436L;
	

}
