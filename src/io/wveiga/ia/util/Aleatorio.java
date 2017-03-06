package io.wveiga.ia.util;

import java.util.Random;

public final class Aleatorio {
	
	private Aleatorio(){}
	
	/**
	 * Algoritmo de Fisher–Yates.
	 * 
	 * @See https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
	 * @param tamanho Tamanho do vetor de inteiros aleatorios gerado.
	 * @return Vetor de números aleatórios de 0 a tamanho.
	 */
	public static int[] vetorInteiros(int tamanho) {
		
		int[] vetor = new int[tamanho];
		Random rg = new Random();
		for (int i = 0; i < tamanho; i++)
		{
		    int r = rg.nextInt(i+1);
		    vetor[i] = vetor[r];
		    vetor[r] = i;
		};
		
		return vetor;
	}
	
}
