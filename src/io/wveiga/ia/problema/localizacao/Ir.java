package io.wveiga.ia.problema.localizacao;

import io.wveiga.ia.ed.Custo;
import io.wveiga.ia.problema.AcaoNominada;

public class Ir extends AcaoNominada<Local> {

	public Ir(Local estadoAlvo, Custo custo) {
		super(Ir.class.getName(), estadoAlvo, custo);
	}

	@Override
	public String toString() {
		return "Ir ["+getEstadoAlvo().getName()+", "+getCusto().toDouble()+"]";
	}
}
