package io.wveiga.ia.algs.busca.naoinfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;
import io.wveiga.ia.util.Preconditions;

public abstract class BuscaNaoInformada<S, T extends Problema<S>> {
	
	private final T problema;
	
	/**
	 * Problema que a busca se propõe a resolver.
	 * @param problema, modelo do problema, não nulo.
	 */
	public BuscaNaoInformada(T problema){
		Preconditions.nonNull(problema);
		this.problema = problema;
	}
	
	public abstract Borda<S> criaBorda();
	
	protected T getProblema() {
		return problema;
	}
	
	/**
	 * Versão interativa da busca em profundidade.
	 * Possui implementação mais complexa, mas não depende da pilha da linguagem, definindo sua própria
	 * pilha para a solução.
	 * 
	 * @return Opcional com a lista de ações caso a solução seja encontrada ou vazio, caso contrário.
	 */
	public Optional<List<Acao<S>>> buscar(){
		// 1. Cria-se um hash para permitir as verificações de nós fechados em O(1)
		Set<S> fechados = new HashSet<>();
		// 1.1 Adiciona-se o estado inicial entre os nós fechados.
		fechados.add(problema.estadoInicial());
		
		// 2. Para recuperar o caminho da busca em profundidade precisamos salvar o pai de cada estado.
		// Isso só é necessário porque não basta encontrar a solução, é preciso conseguir reconstruir o
		// caminho do estado inicial até a solução encontrada.
		Map<Acao<S>, Acao<S>> caminho = new HashMap<>();
		
		// 3. Pilha explícita com os nós ainda não verificados na busca.
		Borda<S> borda = criaBorda();
		// 3.1 Adicionamos o nó inicial.
		borda.insere(Acao.nenhuma(problema.estadoInicial()));
		
		S estado;
		do {
			// 4 Repetimos enquanto não chegarmos à solução e ainda houver nós abertos.
			// 4.1 retiramos a ação no topo da pilha de abertos.
			Acao<S> acao = borda.retira();
			estado = acao.getEstadoAlvo();
			
			// 4.2 Adicionamos o estado alvo no Hash de estados fechados.
			fechados.add(estado);
			
			//4.3 Se o estado atingido é solução a busca termina.
			if (problema.solucao(estado)) {
				// 4.3.1 Precisamos reconstruir o caminho na forma de uma lista de passos.
				return Optional.of(reconstruirCaminhoAte(caminho, acao));
			}
			
			// 4.4 Se ainda não chegamos à solução, verificamos a lista de ações possível a partir do estado atual.
			List<Acao<S>> sucessores = problema.sucessores(estado);
			// 4.5 Para cada estado possível.
			for(Acao<S> proximaAcao : sucessores){
				// 4.6 Se ele não é um retorno a um estado anterior no caminho.
				if (!fechados.contains(proximaAcao.getEstadoAlvo())){
					// 4.7 Selecionamos este estado e o adicionamos no topo da pilha de abertos.
					borda.insere(proximaAcao);
					// 4.8 Memorizamos o caminho e o pai dele para reconstruir o caminho quando a 
					// Solução for encontrada.
					caminho.put(proximaAcao, acao);
				}
			}			
		} while(!problema.solucao(estado) && !borda.vazia());
		
		return Optional.empty();
	}

	private List<Acao<S>> reconstruirCaminhoAte(Map<Acao<S>, Acao<S>> caminho, Acao<S> acao) {
		List<Acao<S>> solucao = new LinkedList<>();
		Acao<S> pai = acao;
		while(pai!=null){
			solucao.add(pai);
			pai = caminho.get(pai);
		}
		Collections.reverse(solucao);
		return solucao;
	}
	

	

}
