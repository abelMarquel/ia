package io.wveiga.ia.problema.npuzzle;

import java.util.Arrays;

import io.wveiga.ia.util.Aleatorio;
import io.wveiga.ia.util.Preconditions;

public class NPuzzleTabuleiro {
	private static final String ESTADO_INVALIDO = "Não é possível instanciar um jogo de tamanho %d com um tabuleiro de %d posições";
	private final int estado[][];
	private final int n;
	
	public NPuzzleTabuleiro(int n, int[][] estado){
		Preconditions.verify(n>=2);
		Preconditions.verify(estado.length*estado[0].length == Math.pow(n, 2), 
				String.format(ESTADO_INVALIDO, n, estado.length*estado[0].length));
		this.n = n;
		this.estado = estado;
	}
	
	public NPuzzleTabuleiro(int n){
		this(n, new int[n][n]);
		
		int[] vetor = Aleatorio.vetorInteiros(n*n);
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				this.estado[i][j] = vetor[j*n+i];
			}
		}
	}
	
	public NPuzzleTabuleiro(){
		this(3);
	}
	
	public int[] gePosicaoVazia(){
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if (estado[i][j] == 0){
					return new int[]{i, j};
				}	
			}
		}
		throw new IllegalStateException("Tabuleiro sem peças vazias!");
	}
	
	
	public boolean eAplicavelA(NPuzzleMovimento movimento){
		Preconditions.nonNull(movimento);
		int[] posicao = gePosicaoVazia();
		int i = posicao[0]+movimento.getVertical();
		int j = posicao[1]+movimento.getHorizontal();
		
		return i>=0 && i<n && j>=0 && j<n;
	}
	
	public NPuzzleTabuleiro aplica(NPuzzleMovimento movimento){
		Preconditions.verify(eAplicavelA(movimento), String.format("O movimento '%s' não é aplicável ao tabuleiro %s\n", movimento,this));
		int[] posicao = gePosicaoVazia();
		int[] novaPos = new int[]{
				posicao[0]+movimento.getVertical(), 
				posicao[1]+movimento.getHorizontal()
		};
		
		int[][] tabuleiro = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				tabuleiro[i][j] = estado[i][j];
			}
		}
		
		tabuleiro[posicao[0]][posicao[1]] = tabuleiro[novaPos[0]][novaPos[1]];
		tabuleiro[novaPos[0]][novaPos[1]] = 0; 
		
		return new NPuzzleTabuleiro(n, tabuleiro);
	}
	
	public boolean eSolucao() {		
		int peca = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if (estado[i][j] != peca) {
					return false;
				}
				peca++;
			}
		}
		return true;
	}
	
	public int getTamanho(){
		return n;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(estado);
		result = prime * result + n;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NPuzzleTabuleiro other = (NPuzzleTabuleiro) obj;
		if (!Arrays.deepEquals(estado, other.estado))
			return false;
		if (n != other.n)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < n; i++){
			builder.append("|");
			for(int j = 0; j < n; j++){
				int peca = estado[i][j];
				if (peca>0) {
					builder.append(peca);
				} else {
					builder.append("-");
				}
				builder.append(" | ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
