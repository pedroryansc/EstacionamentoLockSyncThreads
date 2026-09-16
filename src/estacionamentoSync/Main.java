package estacionamentoSync;

import java.util.List;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int QUANT_VEICULOS = 10;
		
		EstacionamentoSync estacionamento = new EstacionamentoSync();
		
		List<Thread> veiculos = new ArrayList<>(QUANT_VEICULOS);
		
		for(Integer i = 1; i <= QUANT_VEICULOS; i++) {
			Thread veiculo = new Thread(new Veiculo(estacionamento), i.toString());
			
			veiculos.add(veiculo);
		}
		
		System.out.println("Estacionamento Aberto - Synchronized\n");
		
		// A ordem de atendimento dos veículos pode mudar dependendo da execução,
		// Cada veículo tem um tempo definido aleatoriamente para chegar no estacionamento,
		// o que faz com que eles cheguem em momentos diferentes. Além disto, cada
		// veículo fica no estacionamento por um tempo aleatório, com uns saindo
		// antes e outros depois. Porém, caso não houvesse o tempo de viagem, a ordem 
		// de chegada dos veículos também poderia ser diferente a cada execução. Por
		// conta do comportamento não determinístico do escalonador das threads,
		// elas podem ser executadas em ordens de execução diferentes. Isto é
		// perceptível na ordem em que as mensagens de "Veículo X está indo para
		// o estacionamento" são apresentadas.
		for(Thread veiculo : veiculos)
			veiculo.start();
		
		for(Thread veiculo : veiculos) {
			try {
				veiculo.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\n\n===== FIM DO EXPEDIENTE =====\n");
		System.out.println("Ordem de saída dos veículos:");
		
		List<String> saidaVeiculos = estacionamento.getSaidaVeiculos();
		
		for(int i = 0; i < saidaVeiculos.size(); i++)
			System.out.println((i + 1) + ". Veículo " + saidaVeiculos.get(i));
		
		System.out.println("\nTotal arrecadado: R$ " + estacionamento.getTotalArrecadado());
	}
}