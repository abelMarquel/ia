package io.wveiga.ia.util;

public class PreconditionsFailed extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PreconditionsFailed(String message) {
		super(message);
	}

}
