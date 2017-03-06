package io.wveiga.ia;

import java.util.List;
import java.util.Optional;

import io.wveiga.ia.algs.busca.naoinfo.BuscaLargura;
import io.wveiga.ia.algs.busca.naoinfo.BuscaNaoInformada;
import io.wveiga.ia.algs.busca.naoinfo.BuscaProfundidade;
import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.npuzzle.NPuzzle;
import io.wveiga.ia.problema.npuzzle.NPuzzleTabuleiro;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		/*
		 |2 | 3 | , 
		 |- | 1 |
		// NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro(2, new int[][]{{2, 3}, {0, 1}});
		NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro(3);
		NPuzzle puzzle = new NPuzzle(tabuleiro);
		BuscaProfundidade<NPuzzleTabuleiro, NPuzzle> busca = new BuscaProfundidade<>(puzzle);
		Optional<List<Acao<NPuzzleTabuleiro>>> acoes = busca.buscaIterativa();
		if (acoes.isPresent()){
			for (Acao<NPuzzleTabuleiro> acao : acoes.get()) {
				System.  out.println(acao.getEstadoAlvo());
			}
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Tempo total: "+ (end - start));*/
		
		NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro(3);
		NPuzzle puzzle = new NPuzzle(tabuleiro);
		BuscaNaoInformada<NPuzzleTabuleiro, NPuzzle> busca = new BuscaLargura<>(puzzle);
		Optional<List<Acao<NPuzzleTabuleiro>>> acoes = busca.buscar();
		if (acoes.isPresent()){
			for (Acao<NPuzzleTabuleiro> acao : acoes.get()) {
				System.  out.println(acao.getEstadoAlvo());
			}
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Tempo total: "+ (end - start));
		

	}

}
