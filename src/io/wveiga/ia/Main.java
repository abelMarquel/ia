package io.wveiga.ia;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.wveiga.ia.algs.busca.naoinfo.BuscaLargura;
import io.wveiga.ia.algs.busca.naoinfo.BuscaNaoInformada;
import io.wveiga.ia.algs.busca.naoinfo.BuscaProfundidade;
import io.wveiga.ia.ed.Custo;
import io.wveiga.ia.ed.Grafo;
import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.localizacao.Local;
import io.wveiga.ia.problema.localizacao.Localizacao;
import io.wveiga.ia.problema.npuzzle.NPuzzle;
import io.wveiga.ia.problema.npuzzle.NPuzzleTabuleiro;

public class Main {
	
	public static void main(String[] args) {
		
		problemaLocalizacao();	
		
		problemaNPuzzle();
	}
	
	private static void problemaNPuzzle(){
		// 1 - Criando Problema que deve ser resolvido
		NPuzzleTabuleiro tabuleiro = new NPuzzleTabuleiro();
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
	
	private static void problemaLocalizacao(){
		// 1 - Criando Problema que deve ser resolvido
		Grafo<Local> mapa = Grafo.<Local>builder()
				.addLigacaoBiderecional(new Local("Orades"), new Local("Zerind"), Custo.de(71d))
				.addLigacaoBiderecional(new Local("Orades"), new Local("Sibiu"), Custo.de(151d))
				.addLigacaoBiderecional(new Local("Zerid"), new Local("Arad"), Custo.de(75d))
				.addLigacaoBiderecional(new Local("Arad"), new Local("Sibiu"), Custo.de(140d))
				.addLigacaoBiderecional(new Local("Arad"), new Local("Temisoara"), Custo.de(118d))
				.addLigacaoBiderecional(new Local("Temisoara"), new Local("Lugoj"), Custo.de(111d))
				.addLigacaoBiderecional(new Local("Lugoj"), new Local("Mehadia"), Custo.de(70d))
				.addLigacaoBiderecional(new Local("Mehadia"), new Local("Dobreta"), Custo.de(75d))
				.addLigacaoBiderecional(new Local("Dobreta"), new Local("Craiova"), Custo.de(120d))
				.addLigacaoBiderecional(new Local("Craiova"), new Local("RimnicuVilcea"), Custo.de(146d))
				.addLigacaoBiderecional(new Local("Craiova"), new Local("Pitesti"), Custo.de(138d))
				.addLigacaoBiderecional(new Local("RimnicuVilcea"), new Local("Sibiu"), Custo.de(146d))
				.addLigacaoBiderecional(new Local("RimnicuVilcea"), new Local("Pitesti"), Custo.de(97d))
				.addLigacaoBiderecional(new Local("Sibiu"), new Local("Fagaras"), Custo.de(99d))
				.addLigacaoBiderecional(new Local("Fagaras"), new Local("Bucareste"), Custo.de(211d))
				.addLigacaoBiderecional(new Local("Pitesti"), new Local("Bucareste"), Custo.de(101d))
				.build();
				
		Localizacao localizacao = new Localizacao(mapa, new Local("Arad"), new Local("Bucareste"));
		
		System.out.println("Estado Inicial\n"+localizacao.estadoInicial()+"\n\n");
		
		// 2 - Instânciando Buscas e adicionando em uma lista com cada algoritmo.
		List<BuscaNaoInformada<Local, Localizacao>> buscas = Arrays.asList(
				new BuscaProfundidade<>(localizacao),
				new BuscaLargura<>(localizacao)
		);
		
		
		// 3 - Executando as buscas em paralelo.
		buscas.parallelStream()
			.forEach((busca)-> {
				StringBuilder saida = new StringBuilder();
				boolean solucaoEncontrada = false;
				
				System.out.println(busca.getClass().getName()+": Iniciando Busca...");
				long start = System.currentTimeMillis();
				
				// 3.1Obtendo passos para solução ou EMPTY caso a busca não tenha encontrado solução.
				Optional<List<Acao<Local>>> acoes = busca.buscar();
				if (acoes.isPresent()){
					solucaoEncontrada = true;
					
					// 3.2 Caso a solução tenha sido encontrada, imprimir passos.
					for (Acao<Local> acao : acoes.get()) {
						saida.append(acao);
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
