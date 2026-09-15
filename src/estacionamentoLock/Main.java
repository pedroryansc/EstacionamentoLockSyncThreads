package estacionamentoLock;

import java.util.List;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int QUANT_VEICULOS = 10;
		
		EstacionamentoLock estacionamento = new EstacionamentoLock();
		
		List<Thread> veiculos = new ArrayList<>(QUANT_VEICULOS);
		
		for(Integer i = 1; i <= QUANT_VEICULOS; i++) {
			Thread veiculo = new Thread(new Veiculo(estacionamento), i.toString());
			
			veiculos.add(veiculo);
		}
		
		for(Thread veiculo : veiculos)
			veiculo.start();
		
		for(Thread veiculo : veiculos) {
			try {
				veiculo.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}