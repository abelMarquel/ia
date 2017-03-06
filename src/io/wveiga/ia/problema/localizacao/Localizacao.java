package io.wveiga.ia.problema.localizacao;

import java.util.LinkedList;
import java.util.List;

import io.wveiga.ia.ed.Grafo;
import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;
import io.wveiga.ia.util.Preconditions;

public class Localizacao implements Problema<Local>{

	private final Grafo<Local> mapa;
	private final Local origem;
	private final Local destino;
	
	
	
	public Localizacao(Grafo<Local> mapa, Local origem, Local destino) {
		Preconditions.nonNull(mapa, origem, destino);
		this.mapa = mapa;
		this.origem = origem;
		this.destino = destino;
	}

	@Override
	public Local estadoInicial() {
		return origem;
	}

	@Override
	public List<Acao<Local>> sucessores(Local local) {
		List<Acao<Local>> sucessores = new LinkedList<>();
		for(Grafo.Ligacao<Local> ligacao : mapa.getAdjacentes(local)){
			sucessores.add(new Ir(ligacao.getDestino(), ligacao.getCusto()));
		}
		
		return sucessores;
	}

	@Override
	public boolean solucao(Local estado) {
		return estado.equals(destino);
	}

}
