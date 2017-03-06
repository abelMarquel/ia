package io.wveiga.ia.algs.busca.naoinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;
import io.wveiga.ia.util.Preconditions;

/**
 * "Na teoria dos grafos, busca em profundidade (ou busca em profundidade-primeiro, 
 * também conhecido em inglês por Depth-First Search - DFS) é um algoritmo usado para realizar 
 * uma busca ou travessia numa árvore, estrutura de árvore ou grafo. 
 * 
 * Intuitivamente, o algoritmo começa num nó raiz (selecionando algum nó como sendo o raiz, no caso de um grafo)
 *  e explora tanto quanto possível cada um dos seus ramos, antes de retroceder(backtracking).
 *
 * Uma versão da busca em profundidade foi investigada no século XIX pelo matemático francês Charles Pierre Trémaux 
 * como estratégia para solucionar labirintos.
 * 
 * Formalmente, um algoritmo de busca em profundidade realiza uma busca não-informada que progride através da expansão 
 * do primeiro nó filho da árvore de busca, e se aprofunda cada vez mais, até que o alvo da busca seja encontrado ou 
 * até que ele se depare com um nó que não possui filhos (nó folha). Então a busca retrocede (backtrack) 
 * e começa no próximo nó. Numa implementação não-recursiva, todos os nós expandidos recentemente são adicionados a uma pilha, 
 * para realizar a exploração." (WIKIPEDIA)
 * 
 * @author Welington veiga
 * @since 1.0
 * 
 * @see https://pt.wikipedia.org/wiki/Busca_em_profundidade
 * 
 * @param <S> tipo dos estado nos quais a busca vai ser realizada.
 * @param <T> Problema que a busca vai resolver.
 */
public class BuscaProfundidade<S, T extends Problema<S>> {
	
	private final T problema;
	
	/**
	 * Problema que a busca se propõe a resolver.
	 * @param problema, modelo do problema, não nulo.
	 */
	public BuscaProfundidade(T problema){
		Preconditions.nonNull(problema);
		this.problema = problema;
	}
	
	/**
	 * Busca recursivamente a solução, usando a própria pilha da linguagem para
	 * realziar backtracking, possui uma implementação simples e intuitiva, porém pode
	 * ocasionar um StackOverflow caso o número de recursões exceda o limite desta pilha.
	 * 
	 * @return Opcional com a lista de ações caso a solução seja encontrada ou vazio, caso contrário. 
	 */
	public Optional<List<Acao<S>>> buscaRecursiva() {
		// 1. Inicializa o caminho utilizado na solução.
		Queue<Acao<S>> caminho = new LinkedList<>();

		// 2. Inicializa os nós fechados, para evitar que ações que voltem a um estado anteriore sejam repetidas.
		// OBS: Utiliza-se um set como otimização, o tempo de busca no Set para verificar se um nó já existe no mesmo é O(1),
		// verificar no caminho que é uma lista encadeada é O(n).
		Set<S> fechados = new HashSet<>();
		// 2.1 Adiciona o estado inicial na lista de estados fechados, não podemos voltar a ele.
		fechados.add(problema.estadoInicial());

		// 3 Realiza a busca recursiva, acrescentando nós ao caminho e ao conjunto de fechados.
		buscaRecusiva(Acao.nenhuma(problema.estadoInicial()), caminho, fechados);
		
		// 4. Caso haja algum nó no caminho, a solução foi encontrada, caso contrário nada é retornado.
		Optional<List<Acao<S>>> solucao = Optional.empty();
		if (!caminho.isEmpty()) {
			solucao = Optional.of(new ArrayList<>(caminho));
		}
		
		return solucao;
	}
	

	private boolean buscaRecusiva(Acao<S> acaoAtual, Queue<Acao<S>> caminho, Set<S> fechados ) {
		// 1. Verifica-se o estado alvo da ação selecionada.
		S alvo = acaoAtual.getEstadoAlvo();

		// 2. Adiciona-se esse estado aos estados fechados, e a ação selecionada ao caminho.
		caminho.offer(acaoAtual);
		fechados.add(alvo);
		
		// 3. Se esta é a solução, o problema está resolvido, retorna true.
		if (problema.solucao(alvo)) {
			return true;
		}
		
		// 4. Caso contrário, para cada ação possível a partir do estado atual.
		List<Acao<S>> sucessores = problema.sucessores(alvo);
		for(Acao<S> proximaAcao : sucessores){
			// 4.1 Se essa ação não leva a um estado anterior no caminho atual
			if (!fechados.contains(proximaAcao.getEstadoAlvo())){
				// 4.2 A ação é selecionada e a busca continua a partir dessa ação.
				if(buscaRecusiva(proximaAcao, caminho, fechados)){
					return true;
				}
			}
		}
		
		// 5. Se chegamos até aqui, nesse nível da recursão nenhum resultado foi encontrado.
		// Removemos esse estado do caminho e da lista de fechados.
		fechados.remove(alvo);
		caminho.poll();
		
		return false;
	}
	
	/**
	 * Versão interativa da busca em profundidade.
	 * Possui implementação mais complexa, mas não depende da pilha da linguagem, definindo sua própria
	 * pilha para a solução.
	 * 
	 * @return Opcional com a lista de ações caso a solução seja encontrada ou vazio, caso contrário.
	 */
	public Optional<List<Acao<S>>> buscaIterativa(){
		// 1. Cria-se um hash para permitir as verificações de nós fechados em O(1)
		Set<S> fechados = new HashSet<>();
		// 1.1 Adiciona-se o estado inicial entre os nós fechados.
		fechados.add(problema.estadoInicial());
		
		// 2. Para recuperar o caminho da busca em profundidade precisamos salvar o pai de cada estado.
		// Isso só é necessário porque não basta encontrar a solução, é preciso conseguir reconstruir o
		// caminho do estado inicial até a solução encontrada.
		Map<Acao<S>, Acao<S>> caminho = new HashMap<>();
		
		// 3. Pilha explícita com os nós ainda não verificados na busca.
		Stack<Acao<S>> abertos = new Stack<>();
		// 3.1 Adicionamos o nó inicial.
		abertos.add(Acao.nenhuma(problema.estadoInicial()));
		
		System.out.println("Estado Inicial\n"+problema.estadoInicial()+"\n\n");
		
		S estado;
		do {
			// 4 Repetimos enquanto não chegarmos à solução e ainda houver nós abertos.
			// 4.1 retiramos a ação no topo da pilha de abertos.
			Acao<S> acao = abertos.pop();
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
					abertos.add(proximaAcao);
					// 4.8 Memorizamos o caminho e o pai dele para reconstruir o caminho quando a 
					// Solução for encontrada.
					caminho.put(proximaAcao, acao);
				}
			}			
		} while(!problema.solucao(estado) && !abertos.isEmpty());
		
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
