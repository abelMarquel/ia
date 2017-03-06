package io.wveiga.ia.util;

public final class Preconditions {
	
	private Preconditions(){}
	
	private static void assertThat(boolean condition, String message){
		if (!condition)
			throw new PreconditionsFailed(message);
	}

	public static void verify(boolean condition, String message) {
		assertThat(condition, message);
	}
	
	public static void verify(boolean condition) {
		verify(condition, "Pre condition failed.");
	}
	public static void verify(boolean... conditions) {
		for (boolean condition : conditions) {
			verify(condition, "Pre condition failed.");
		}
	}

	public static void nonNull(Object reference, String message) {
		assertThat(reference!=null, message);
	}
	
	public static void nonNull(Object referencia) {
		nonNull(referencia, "Required object is null.");
	}
	
	public static void nonNull(Object...referencias) {
		for (Object referencia : referencias) {
			nonNull(referencia);
		}
	}
}
