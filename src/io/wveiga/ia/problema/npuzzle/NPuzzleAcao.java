package io.wveiga.ia.problema.npuzzle;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Custo;
import io.wveiga.ia.util.Preconditions;

public class NPuzzleAcao implements Acao<NPuzzleTabuleiro> {
		
	private final NPuzzleMovimento movimento;
	private final NPuzzleTabuleiro tabuleiro;
	
	
	public NPuzzleAcao(NPuzzleMovimento movimento, NPuzzleTabuleiro tabuleiro) {
		Preconditions.nonNull(movimento, tabuleiro);
		Preconditions.verify(tabuleiro.eAplicavelA(movimento));
		this.movimento = movimento;
		this.tabuleiro = tabuleiro;
	}

	@Override
	public NPuzzleTabuleiro getEstadoAlvo() {
		return tabuleiro.aplica(movimento);
	}

	@Override
	public Custo getCusto() {
		return Custo.UNIFORME;
	}

	@Override
	public String getNone() {
		StringBuilder builder = new StringBuilder();
		builder.append(movimento);
		builder.append("\n");
		builder.append(tabuleiro);
		builder.append("\n\n");
		return builder.toString();
	}
	
		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movimento == null) ? 0 : movimento.hashCode());
		result = prime * result + ((tabuleiro == null) ? 0 : tabuleiro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NPuzzleAcao other = (NPuzzleAcao) obj;
		if (movimento != other.movimento)
			return false;
		if (tabuleiro == null) {
			if (other.tabuleiro != null)
				return false;
		} else if (!tabuleiro.equals(other.tabuleiro))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getNone();
	}
}
