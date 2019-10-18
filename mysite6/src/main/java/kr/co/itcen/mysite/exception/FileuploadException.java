package kr.co.itcen.mysite.exception;

public class FileuploadException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public FileuploadException() {
		super("Fileupload Exception");
	}
	
	public FileuploadException(String message) {
		super(message);
	}
}
