package estacionamentoSync;

public class Veiculo implements Runnable {
	private EstacionamentoSync estacionamento;
	
	public Veiculo(EstacionamentoSync estacionamento) {
		this.estacionamento = estacionamento;
	}
	
	public void run() {
		estacionamento.usarEstacionamento();
	}
}
