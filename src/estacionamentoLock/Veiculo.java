package estacionamentoLock;

public class Veiculo implements Runnable {
	private EstacionamentoLock estacionamento;
	
	public Veiculo(EstacionamentoLock estacionamento) {
		this.estacionamento = estacionamento;
	}
	
	@Override
	public void run() {
		estacionamento.usarEstacionamento();
	}
}
