package io.wveiga.ia;

import java.util.Arrays;
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
		
		// 1 - Criando Problema que deve ser resolvido
		NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro(2);
		NPuzzle puzzle = new NPuzzle(tabuleiro);
		System.out.println("Estado Inicial\n"+puzzle.estadoInicial()+"\n\n");
		
		// 2 - Instânciando Buscas e adicionando em uma lista com cada algoritmo.
		List<BuscaNaoInformada<NPuzzleTabuleiro, NPuzzle>> buscas = Arrays.asList(
				new BuscaProfundidade<>(puzzle),
				new BuscaLargura<>(puzzle)
		);
		
		
		// 3 - Executando as buscas em paralelo.
		buscas.parallelStream()
			.forEach((busca)-> {
				StringBuilder saida = new StringBuilder();
				boolean solucaoEncontrada = false;
				
				System.out.println(busca.getClass().getName()+": Iniciando Busca...");
				long start = System.currentTimeMillis();
				
				// 3.1Obtendo passos para solução ou EMPTY caso a busca não tenha encontrado solução.
				Optional<List<Acao<NPuzzleTabuleiro>>> acoes = busca.buscar();
				if (acoes.isPresent()){
					solucaoEncontrada = true;
					
					// 3.2 Caso a solução tenha sido encontrada, imprimir passos.
					for (Acao<NPuzzleTabuleiro> acao : acoes.get()) {
						saida.append(acao.getEstadoAlvo());
						saida.append('\n');
					}
				}
				
				long end = System.currentTimeMillis();
				
				System.out.println(busca.getClass().getName()
						+ ": busca finalizada, solução encontrada: "
						+solucaoEncontrada+" Tempo (ms): "
						+ (end - start)
						+ "\n"
						+ saida);
			});
	}

}
