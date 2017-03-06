package io.wveiga.ia.problema.npuzzle;

import java.util.ArrayList;
import java.util.List;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;
import io.wveiga.ia.util.Preconditions;

public class NPuzzle implements Problema<NPuzzleTabuleiro> {
	
	private final NPuzzleTabuleiro estadoInicial;
	
	public NPuzzle(int n) {
		Preconditions.verify(n>=2);
		this.estadoInicial = new NPuzzleTabuleiro(n);
	}

	public NPuzzle(NPuzzleTabuleiro tabuleiro) {
		Preconditions.nonNull(tabuleiro);
		this.estadoInicial = tabuleiro;
	}

	
	@Override
	public NPuzzleTabuleiro estadoInicial() {
		return estadoInicial;
	}

	@Override
	public List<Acao<NPuzzleTabuleiro>> sucessores(NPuzzleTabuleiro estado) {
		Preconditions.nonNull(estado);
		List<Acao<NPuzzleTabuleiro>> acoes = new ArrayList<>();
		for(NPuzzleMovimento movimento : NPuzzleMovimento.values()){
			if(estado.eAplicavelA(movimento)){
				acoes.add(new NPuzzleAcao(movimento, estado));
			}
		}
		return acoes;
	}

	@Override
	public boolean solucao(NPuzzleTabuleiro estado) {
		return estado.eSolucao();
	}

}
