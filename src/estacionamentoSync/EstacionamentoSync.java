package estacionamentoSync;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class EstacionamentoSync {
	private Random sorteador = new Random();
	private List<String> saidaVeiculos = new ArrayList<>();
	private int totalArrecadado = 0;
	private final int TARIFA = 5;
	private final int TEMPO_CANCELA = 3;
	
	public List<String> getSaidaVeiculos(){
		return saidaVeiculos;
	}
	
	public int getTotalArrecadado() {
		return totalArrecadado;
	}
	
	public void usarEstacionamento() {
		String numVeiculo = Thread.currentThread().getName();
		
		// Viagem do veículo até o estacionamento (Tempo aleatório)
		System.out.println("Veículo " + numVeiculo + " está indo para o estacionamento.");
		
		int tempoViagem = sorteador.nextInt(5, 51);
		
		try {
			Thread.sleep(tempoViagem * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nVeículo " + numVeiculo + " chegou.");
		
		// Executa o trecho com a palavra-chave para passar pela cancela
		// (se outro veículo/thread estiver executando o trecho, o veículo/thread atual aguarda)
		synchronized(this) {
			System.out.println("\nVeículo " + numVeiculo + " pegou o ticket do estacionamento.");
				
			abrirCancela(numVeiculo, true);
		}
		
		// O veículo permanece um tempo (aleatório) no estacionamento
		int tempoEstacionado = sorteador.nextInt(10, 21);
		try {
			Thread.sleep(tempoEstacionado * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nVeículo " + numVeiculo + " permaneceu " + tempoEstacionado + " segundos no estacionamento e foi para a saída.");
		
		// Executa o trecho synchronized para passar pela cancela (se estiver ocupada, aguarda)
		synchronized(this) {
			System.out.println("\nVeículo " + numVeiculo + " chegou na saída e pagou pelo ticket.");
			
			// Pagamento pelo estacionamento é adicionado ao total arrecadado
			totalArrecadado += TARIFA;
			
			abrirCancela(numVeiculo, false);
		}
	}
	
	public void abrirCancela(String numVeiculo, boolean entrando) {
		System.out.println("\nAbrindo a cancela para o veículo " + numVeiculo + ".");
		
		try {
			Thread.sleep(TEMPO_CANCELA * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		if(entrando)
			System.out.println("\nVeículo " + numVeiculo + " entrando no estacionamento.");
		else {
			System.out.println("\nVeículo " + numVeiculo + " saindo do estacionamento.");
			
			saidaVeiculos.add(numVeiculo);
		}
		
		try {
			Thread.sleep(4000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nFechando a cancela após o veículo " + numVeiculo + " passar.");
		
		try {
			Thread.sleep(TEMPO_CANCELA * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nCancela fechada.");
	}
}
