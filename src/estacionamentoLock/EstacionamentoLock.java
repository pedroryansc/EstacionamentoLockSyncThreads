package estacionamentoLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class EstacionamentoLock {
	private Lock lock = new ReentrantLock();
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
		
		// Tenta adquirir a permissão do Lock para passar pela cancela (se estiver ocupada, o veículo aguarda)
		boolean adquiriuLock = lock.tryLock();
		
		try {
			if(!adquiriuLock) {
				System.out.println("\nVeículo " + numVeiculo + " está aguardando na fila.");
				lock.lock();
			}
			
			System.out.println("\nVeículo " + numVeiculo + " pegou o ticket do estacionamento.");
			
			abrirCancela(numVeiculo, true);
		} finally {
			// Libera o uso da cancela (após o veículo passar, se não houver erros)
			lock.unlock();
		}
		
		// O veículo permanece um tempo (aleatório) no estacionamento
		int tempoEstacionado = sorteador.nextInt(10, 21);
		try {
			Thread.sleep(tempoEstacionado * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nVeículo " + numVeiculo + " permaneceu " + tempoEstacionado + " segundos no estacionamento e foi para a saída.");
		
		// Adquire a permissão para passar pela cancela (se estiver ocupada, aguarda)
		lock.lock();
		
		try {
			System.out.println("\nVeículo " + numVeiculo + " chegou na saída e pagou pelo ticket.");
			
			// Pagamento pelo estacionamento é adicionado ao total arrecadado
			totalArrecadado += TARIFA;
			
			abrirCancela(numVeiculo, false);
		} finally {
			// Libera o uso da cancela (após o veículo sair, se não houver erros)
			lock.unlock();
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
