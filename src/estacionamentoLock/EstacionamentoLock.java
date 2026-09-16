package estacionamentoLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class EstacionamentoLock {
	private Lock lock = new ReentrantLock();
	private Random sorteador = new Random();
	private int totalArrecadado = 0;
	private final int TARIFA = 5;
	private final int TEMPO_CANCELA = 3;
	
	public int getTotalArrecadado() {
		return totalArrecadado;
	}
	
	public void usarEstacionamento() {
		String numVeiculo = Thread.currentThread().getName();
		
		// Viagem do veículo até o estacionamento (Tempo aleatório)
		System.out.println("Veículo " + numVeiculo + " está indo para o estacionamento.");
		
		int tempoViagem = sorteador.nextInt(5, 11);
		
		try {
			Thread.sleep(tempoViagem * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veículo " + numVeiculo + " chegou.");
		
		// Adquire a permissão do Lock para passar pela cancela (se estiver ocupada, o veículo aguarda)
		// tryLock()?
		lock.lock();
		
		System.out.println("Veículo " + numVeiculo + " pegou o ticket do estacionamento.");
		abrirCancela(numVeiculo, true);
		
		// Libera o uso da cancela após o veículo passar
		lock.unlock();
		
		// O veículo permanece um tempo (aleatório) no estacionamento
		int tempoEstacionado = sorteador.nextInt(10, 21);
		try {
			Thread.sleep(tempoEstacionado * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nVeículo " + numVeiculo + " permaneceu " + tempoEstacionado + " segundos no estacionamento.\n");
		System.out.println("Veículo " + numVeiculo + " foi para a saída.");
		
		// Adquire a permissão para passar pela cancela (se estiver ocupada, aguarda)
		lock.lock();
		
		System.out.println("Veículo " + numVeiculo + " chegou na saída e pagou pelo ticket.");
		
		// Pagamento pelo estacionamento é adicionado ao total arrecadado
		totalArrecadado += TARIFA;
		
		abrirCancela(numVeiculo, false);
		
		// Libera o uso da cancela após o veículo sair
		lock.unlock();
	}
	
	public void abrirCancela(String numVeiculo, boolean entrando) {
		System.out.println("Abrindo a cancela para o veículo " + numVeiculo + ".");
		
		try {
			Thread.sleep(TEMPO_CANCELA * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		if(entrando)
			System.out.println("Veículo " + numVeiculo + " entrando no estacionamento.");
		else
			System.out.println("Veículo " + numVeiculo + " saindo do estacionamento.");
		
		try {
			Thread.sleep(4000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fechando a cancela após o veículo " + numVeiculo + " passar.");
		
		try {
			Thread.sleep(TEMPO_CANCELA * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cancela fechada.");
	}
}
