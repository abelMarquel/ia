package io.wveiga.ia.ed;

import io.wveiga.ia.util.Preconditions;

/**
 * Classe que representa o custo de uma ação.
 * 
 * O custo encapsula um double que representa esse valor.
 * 
 * Esta classe também disponibiliza três custos estáticos:
 * <ul>
 * 	<li>INFINITO, que é um custo não mensurável, ou impossível.</li>
 *  <li>ZERO, que é uma ação sem custo.</li>
 *  <li>UNIFORME, que é uma ação com custo padrão 1.</li>
 * </ul>
 * 
 * @author Welington Veiga
 * @since 1
 *
 */
public class Custo {
	
	public static Custo INFINITO = new Custo(null);
	
	public static Custo ZERO = new Custo(0d);
	
	public static Custo UNIFORME = new Custo(1d);
	
	private final Double valor;
	
	private Custo(Double valor){
		this.valor = valor;
	}
	
	public Double toDouble(){
		return valor;
	}
	
	public boolean finito(){
		return valor!=null;
	}
	
	public static Custo de(double valor) {
		Preconditions.nonNull(valor, "Custo não pode ser nulo");
		Preconditions.verify(valor >= 0, "Custo não pode ser negativo");
		return new Custo(valor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Custo other = (Custo) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Custo [valor=" + valor + "]";
	}
}
