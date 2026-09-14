package estacionamento;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		final int QUANT_VEICULOS = 10;
		
		EstacionamentoLock estacionamento = new EstacionamentoLock();
		
		List<Thread> veiculos = new ArrayList<>(QUANT_VEICULOS);
		
		Random sorteador = new Random();
		int tempoEstacionado;
		
		for(Integer i = 1; i <= QUANT_VEICULOS; i++) {
			tempoEstacionado = sorteador.nextInt(10, 21);
			
			Thread veiculo = new Thread(new Veiculo(estacionamento, tempoEstacionado), i.toString());
			
			veiculos.add(veiculo);
		}
	}
}