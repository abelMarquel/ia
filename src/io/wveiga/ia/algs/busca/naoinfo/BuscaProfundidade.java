package io.wveiga.ia.algs.busca.naoinfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;

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
public class BuscaProfundidade<S, T extends Problema<S>> extends BuscaNaoInformada<S, T>{
	

	public BuscaProfundidade(T problema) {
		super(problema);
	}

	@Override
	public Borda<S> criaBorda() {
		return new BordaPilha<>();
	}
	
	
	/**
	 * Busca recursivamente a solução, usando a própria pilha da linguagem para
	 * realziar backtracking, possui uma implementação simples e intuitiva, porém pode
	 * ocasionar um StackOverflow caso o número de recursões exceda o limite desta pilha.
	 * 
	 * @return Opcional com a lista de ações caso a solução seja encontrada ou vazio, caso contrário. 
	 */
	public Optional<List<Acao<S>>> buscaRecursiva() {
		
		T problema = getProblema();
		
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
		
		T problema = getProblema();
		
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
	 * Comportamento para inserção e remoção de ações (nós) da borda de uma Busca em Profundidade.
	 * 	  
	 * Basicamente decora uma pilha.
	 * 
	 * @author Welington Veiga
	 *
	 * @param <S> Tipo de estado para onde as ações da borda podem levar.
	 */
	private static class BordaPilha<S> implements Borda<S> {
		
		/**
		 * OBS: A classe Stack do Java herda de Vector, o que não é desejével, não precisamos 
		 * possuir suporte a acesso concorrente nesse contexto.
		 * 
		 * Por esse motivo estamos usando uma Lista Duplamente Encadeada para realizar o comportamento da pilha, 
		 * sempre adicionando e removendo do final.
		 */
		private final LinkedList<Acao<S>> pilha = new LinkedList<>();
		
		/**
		 * Insere uma ação na borda, isto é, adiciona no fim da fila.
		 */
		@Override
		public void insere(Acao<S> acao) {
			pilha.addLast(acao);
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
			return pilha.removeLast();
		}
		

		/**
		 * Retorna true caso a fila esteja vazia, falso caso contrário.
		 * 
		 */		
		@Override
		public boolean vazia() {
			return pilha.isEmpty();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((pilha == null) ? 0 : pilha.hashCode());
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
			BordaPilha<?> other = (BordaPilha<?>) obj;
			if (pilha == null) {
				if (other.pilha != null)
					return false;
			} else if (!pilha.equals(other.pilha))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "BordaPilha [pilha=" + pilha + "]";
		}
				
	}	
}
