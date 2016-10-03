package ca.polymtl.inf4410.tp1.client;

public class FakeServer {
	int execute(int a, int b) {
		return a + b;
	}
	/*
	 * Méthode accessible par RMI. Tester les impacts des arguments 
	 * dependamment de la taille du tableau.
	 * .
	 */
	public void  executeArray(byte[] byteArray) {
		
	} 
}
