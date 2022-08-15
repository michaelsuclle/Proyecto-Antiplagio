import java.text.Collator;
public class probandoImplementacionComparator {
	public static void main(String[] args){
		String cadena1 = "alo5";
		String cadena2 = "hola4";

		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.SECONDARY);


		int indicecomparador = comparador.compare(cadena1, cadena2);
		if (indicecomparador == 0) {
			System.out.println("Las cadenas son iguales");
		} else {
			if (indicecomparador < 0) {
				System.out.println("La primera cadena va antes "+indicecomparador);
			} else {
				System.out.println("La primera cadena va despues "+indicecomparador);
			}
		}

	}

}

