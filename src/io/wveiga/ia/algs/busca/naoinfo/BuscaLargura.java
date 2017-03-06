package io.wveiga.ia.algs.busca.naoinfo;

import java.util.LinkedList;
import java.util.Queue;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;


/**
 * "Na teoria dos grafos, busca em largura (ou busca em amplitude, também conhecido em inglês por Breadth-First Search - BFS) 
 * é um algoritmo de busca em grafos utilizado para realizar uma busca ou travessia num grafo e estrutura de dados
 *  do tipo árvore expandindo sempre o nó mais raso aberto." (WIKIPEDIA)
 * 
 * @author Welington veiga
 * @since 1.0
 * 
 * @see https://pt.wikipedia.org/wiki/Busca_em_largura
 * 
 * @param <S> tipo dos estado nos quais a busca vai ser realizada.
 * @param <T> Problema que a busca vai resolver.
 */
public class BuscaLargura<S, T extends Problema<S>> extends BuscaNaoInformada<S, T> {

	public BuscaLargura(T problema) {
		super(problema);
	}

	@Override
	public Borda<S> criaBorda() {
		return new BordaFila<>();
	}

	/**
	 * Comportamento para inserção e remoção de ações (nós) da borda de uma Busca em Largura (ou Extensão).
	 * 	  
	 * Basicamente decora uma fila.
	 * 
	 * @author Welington Veiga
	 *
	 * @param <S> Tipo de estado para onde as ações da borda podem levar.
	 */
	private static class BordaFila<S> implements Borda<S> {
		
		private final Queue<Acao<S>> fila = new LinkedList<>();
		
		/**
		 * Insere uma ação na borda, isto é, adiciona no fim da fila.
		 */
		@Override
		public void insere(Acao<S> acao) {
			fila.add(acao);
		}


		/**
		 * Retira uma ação da borda, isto é, retorna e remove da primeira posição da fila.
		 * 
		 * @throws IllegalStateEsception se a fila estiver vazia.
		 */
		@Override
		public Acao<S> retira() {
			if (vazia()){
				throw new IllegalStateException("Fila vazia");
			} 
			return fila.poll();
		}
		

		/**
		 * Retorna true caso a fila esteja vazia, falso caso contrário.
		 * 
		 */		@Override
		public boolean vazia() {
			return fila.isEmpty();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fila == null) ? 0 : fila.hashCode());
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
			BordaFila<?> other = (BordaFila<?>) obj;
			if (fila == null) {
				if (other.fila != null)
					return false;
			} else if (!fila.equals(other.fila))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "BordaFila [fila=" + fila + "]";
		}
				
	}
	
}
