package io.wveiga.ia.algs.busca.naoinfo;

import io.wveiga.ia.problema.Acao;

/**
 * Os algoritmos de busca não informada possuem uma forma padrão, diferindo principalmente na forma como a borda é tratada.
 * 
 * Essa interface define o comportamento comum da Borda para os algoritmos de busca não informada.
 * 
 * @author Welington Veiga
 *
 * @param <T> Tipo de estado para qual as ações presentes na borda podem levar.
 */
interface Borda<T> {

	void insere(Acao<T> acao);
	
	Acao<T>  retira();
	
	boolean vazia();
}
