package io.wveiga.ia.problema;

import io.wveiga.ia.util.Preconditions;

/**
 * Implementação base da ação, que recebe as informações de nome, estado alvo e custo em seu construtor.
 * 
 * @author welingtonveiga
 *
 * @param <T> tipo do estado resultado da aplicação desta ação.
 */
public class AcaoNominada<T> implements Acao<T> {

	private final String nome;
	private final T estadoAlvo;
	private final Custo custo;
	
	public AcaoNominada(String nome, T estadoAlvo, Custo custo){
		Preconditions.nonNull(nome, estadoAlvo, custo);
		this.nome = nome;
		this.estadoAlvo = estadoAlvo;
		this.custo = custo;
	}
	
	@Override
	public T getEstadoAlvo() {
		return estadoAlvo;
	}

	@Override
	public Custo getCusto() {
		return custo;
	}

	@Override
	public String getNone() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custo == null) ? 0 : custo.hashCode());
		result = prime * result + ((estadoAlvo == null) ? 0 : estadoAlvo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		AcaoNominada<?> other = (AcaoNominada<?>) obj;
		if (custo == null) {
			if (other.custo != null)
				return false;
		} else if (!custo.equals(other.custo))
			return false;
		if (estadoAlvo == null) {
			if (other.estadoAlvo != null)
				return false;
		} else if (!estadoAlvo.equals(other.estadoAlvo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AcaoNominada [nome=" + nome + ", estadoAlvo=" + estadoAlvo + ", custo=" + custo + "]";
	}	
}
