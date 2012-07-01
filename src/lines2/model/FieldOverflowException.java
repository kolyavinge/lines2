package lines2.model;

public class FieldOverflowException extends RuntimeException {

	private static final long serialVersionUID = -6141377840087935264L;

	public FieldOverflowException() {
		super();
	}

	public FieldOverflowException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public FieldOverflowException(String detailMessage) {
		super(detailMessage);
	}

	public FieldOverflowException(Throwable throwable) {
		super(throwable);
	}
}
