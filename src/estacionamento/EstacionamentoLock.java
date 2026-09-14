package estacionamento;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EstacionamentoLock {
	private Lock lock = new ReentrantLock();
	
	public void usarEstacionamento(int tempoEstacionado) {
		String numVeiculo = Thread.currentThread().getName();
		
		lock.lock();
		
		System.out.println("\nVeículo " + numVeiculo + " chegou no estacionamento:\n");
		System.out.println("Abrindo a cancela para o veículo " + numVeiculo + ".");
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veículo " + numVeiculo + " entrando no estacionamento.");
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fechando a cancela após o veículo " + numVeiculo + " passar.");
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cancela fechada.");
		
		lock.unlock();
		
		try {
			Thread.sleep(tempoEstacionado * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nVeículo " + numVeiculo + " permaneceu " + tempoEstacionado + " segundos no estacionamento.\n");
		
		lock.lock();
	}
}
