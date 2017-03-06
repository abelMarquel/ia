package io.wveiga.ia.problema;

/**
 * Abstração para uma ação para resolver um problema, que possui um <b>estado alvo</b> após sua realização, 
 * um <b>custo</b> para ser realizada e um <b>nome</b> que descreve o tipo de ação referida.
 * 
 * @author Welington Veiga
 *
 * @param <T> tipo de estado Origem / Destino sobre o qual esta ação se aplica / resulta.
 */
public interface Acao<T> {
	
	static <S> NenumaAcao<S> nenhuma(S estado) {
		return new NenumaAcao<S>(estado);
	}
	
	/**
	 * Estado destino após esta ação ser aplicada.
	 * @return Estado destino.
	 */
	T getEstadoAlvo();
	
	/**
	 * Custo para realizar a ação.
	 * @return custo para realizar a ação.
	 */
	Custo getCusto();
	

	/**
	 * Nome da ação, para sua identificação.
	 * @return nome da ação.
	 */
	String getNone();
	
	/**
	 * Implementação da ação nula, isto é, nenhuma ação a ser realizada.
	 * 
	 * Ela tem custo zero e retorna o próprio estado em que está.
	 * 
	 * @author Welington Veiga
	 *
	 * @param <T> tipo do estado que será recebido/retornado.
	 */
	static class NenumaAcao<T> implements Acao<T> {
		private final T estado;
		private NenumaAcao(T estado) {
			this.estado = estado;
		}
		@Override
		public T getEstadoAlvo() {
			return estado;
		}
		@Override
		public Custo getCusto() {
			return Custo.ZERO;
		}
		@Override
		public String getNone() {
			return "Nenhuma ação";
		}
		
		@Override
		public String toString() {
			return getNone();
		}
	}
	
}
