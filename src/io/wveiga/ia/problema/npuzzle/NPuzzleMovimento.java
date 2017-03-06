package io.wveiga.ia.problema.npuzzle;

public enum NPuzzleMovimento {
	ESQUERDA(0,-1), DIREITA(0,+1), CIMA(-1, 0), BAIXO(+1,0);
	
	private final int vertical;
	private final int horizontal;
	
	private NPuzzleMovimento(int vertical, int horizontal) {
		this.vertical = vertical;
		this.horizontal = horizontal;
	}

	public int getVertical() {
		return vertical;
	}

	public int getHorizontal() {
		return horizontal;
	}	
}
