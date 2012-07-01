package lines2.model;

public class MoveBallException extends RuntimeException {

	private static final long serialVersionUID = -4267512831437978373L;

	public MoveBallException() {
		super();
	}

	public MoveBallException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public MoveBallException(String detailMessage) {
		super(detailMessage);
	}

	public MoveBallException(Throwable throwable) {
		super(throwable);
	}
}
