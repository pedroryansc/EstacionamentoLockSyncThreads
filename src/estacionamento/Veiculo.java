package estacionamento;

public class Veiculo implements Runnable {
	private EstacionamentoLock estacionamento;
	private int tempoEstacionado;
	
	public Veiculo(EstacionamentoLock estacionamento, int tempoEstacionado) {
		this.estacionamento = estacionamento;
		this.tempoEstacionado = tempoEstacionado;
	}
	
	@Override
	public void run() {
		estacionamento.usarEstacionamento(tempoEstacionado);
	}
}
