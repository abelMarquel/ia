package io.wveiga.ia.problema;

import java.util.List;

/**
 * Segundo Russel e Norving (Inteligência Artificial, Editora Nova, 2004).
 * Um problema é definido por quatro itens:
 * 1. Estado inicial ex., “em Arad"
 * 2. Ações ou função sucessor S(x) = conjunto de pares estado-ação
 *  – ex., S(Arad) = {<Arad  Zerind, Zerind>, … }
 * 3. Teste de objetivo, pode ser
 * – explícito, ex., x = “em Bucharest"
 * – implícito, ex., Cheque-mate(x)
 * –
 * 4. Custo de caminho (aditivo)
 * – ex., soma das distâncias, número de ações executadas, etc.
 * – c(x,a,y) é o custo do passo, que deve ser sempre ≥ 0
 * • Uma solução é uma sequência de ações que levam do estado inicial para o estado objetivo.
 * • Uma solução ótima é uma solução com o menor custo de caminho.
 *
 * Esta interface abstrai um problema, onde são definidos seus componentes de definição obrigatória:
 * <ul>
 *  <li>Estado Inicial (1)</li>
 *  <li>Sucessores de determinado estado (2), onde cada ação possível possui um custo associado e um estado destino(4).</li>
 *  <li>Verifição se um estado é solução para o problema (3).</li>
 * </ul> 
 * 
 * @author Welington Veiga
 * @see http://aima.cs.berkeley.edu/ Cap. 3
 * @since 1.0
 */
public interface Problema<T> {
	
	/**
	 * Retorna o estado inicial do problema.
	 * 
	 * @return Estado Inicial do problema.
	 */
	T estadoInicial();
	
	/**
	 * Retorna a lista de sucessores de determinado estado, que consiste em uma lista de ações possíveis, 
	 * cada uma com um custo associado e um estado destino.
	 * @param estado Estado a partir do qual a lista de ações possíveis deve ser obtida.
	 * @return lista de ações possíveis a partir de determinado estado.
	 */
	List<Acao<T>> sucessores(T estado);
	
	/**
	 * Verifica se um determinado estado é solução para o problema.
	 * @param estado estado que se deseja verificar se é solução.
	 * @return verdadeiro, se o estado é solução, caso contrário retorna falso.
	 */
	boolean solucao(T estado);
		
}
