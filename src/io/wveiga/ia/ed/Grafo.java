package io.wveiga.ia.ed;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.wveiga.ia.ed.Grafo.GrafoBuilder;

public class Grafo<T> {

	private final Map<T, Map<T, Custo>> adjacencias;
	
	private Grafo (Map<T, Map<T, Custo>> adjacencias){
		this.adjacencias = adjacencias;
	}
	
	public Optional<Custo> getCustoLigacaoEntre(T origem, T destino) {
		Optional<Custo> custo = Optional.empty();
		
		Map<T, Custo> adjacentes = adjacencias.get(origem);
		if (adjacentes.containsKey(destino)){
			custo = Optional.of(adjacentes.get(destino));
		}
		
		return custo;
	}
	
	public Set<Ligacao<T>> getAdjacentes(T vertice) {
		Set<Ligacao<T>> ligacoes = new HashSet<>();
		
		for (Map.Entry<T, Custo> adjacentes : adjacencias.getOrDefault(vertice, Collections.emptyMap()).entrySet()) {
			ligacoes.add(new Ligacao<>(adjacentes.getKey(), adjacentes.getValue()));
		}
				
		return ligacoes;
	}
	
	public static <T> GrafoBuilder<T> builder(){
		return new GrafoBuilder<>();
	}
	
	public static class GrafoBuilder<T>{
		
		private final Map<T, Map<T, Custo>> adjacencias = new HashMap<>();
		
		private GrafoBuilder(){}
		
		public GrafoBuilder<T> addVertice(T vertice) {
			adjacencias.putIfAbsent(vertice, new HashMap<>());
			return this;
		}
		
		public GrafoBuilder<T> addLigacao(T origem, T destino, Custo custo) {
			this.addVertice(origem);
			this.addVertice(destino);
			adjacencias.get(origem).put(destino, custo);
			return this;
		}
		
		public GrafoBuilder<T> addLigacaoBiderecional(T um, T outro, Custo custo) {
			addLigacao(um, outro, custo);
			addLigacao(outro, um, custo);
			return this;
		}
		
		public Grafo<T> build(){
			return new Grafo<>(adjacencias);
		}
		
	}
	
	public static class Ligacao<T> {
		
		private final T destino;
		private final Custo custo;
		
		private Ligacao(T destino, Custo custo){
			this.destino = destino;
			this.custo = custo;
		}

		public T getDestino() {
			return destino;
		}
		
		public Custo getCusto() {
			return custo;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((custo == null) ? 0 : custo.hashCode());
			result = prime * result + ((destino == null) ? 0 : destino.hashCode());
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
			Ligacao<?> other = (Ligacao<?>) obj;
			if (custo == null) {
				if (other.custo != null)
					return false;
			} else if (!custo.equals(other.custo))
				return false;
			if (destino == null) {
				if (other.destino != null)
					return false;
			} else if (!destino.equals(other.destino))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Ligacao [destino=" + destino + ", custo=" + custo + "]";
		}
		
	}
	
	
}
