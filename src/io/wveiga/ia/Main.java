package io.wveiga.ia;

import java.util.List;
import java.util.Optional;

import io.wveiga.ia.algs.busca.naoinfo.BuscaProfundidade;
import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.npuzzle.NPuzzle;
import io.wveiga.ia.problema.npuzzle.NPuzzleTabuleiro;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		// 1 - Criando Problema que deve ser resolvido
		NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro(3);
		NPuzzle puzzle = new NPuzzle(tabuleiro);
		
		// 2 - Instânciando Busca.
		BuscaProfundidade<NPuzzleTabuleiro, NPuzzle> busca = new BuscaProfundidade<>(puzzle);
		
		// 3 - Obtendo passos para solução ou EMPTY caso a busca não tenha encontrado solução.
		Optional<List<Acao<NPuzzleTabuleiro>>> acoes = busca.buscaIterativa();
		if (acoes.isPresent()){
			// 3.1 Caso a solução tenha sido encontrada, imprimir passos.
			for (Acao<NPuzzleTabuleiro> acao : acoes.get()) {
				System.out.println(acao.getEstadoAlvo());
			}
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Tempo total: "+ (end - start));
	}

}
