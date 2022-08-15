import java.util.ArrayList;
public class Preprocesador {
	static String textooriginal;//texto que es 100% original 
	static ArrayList<ArbolBST> arreglodearboles= new ArrayList<ArbolBST>();//en cada arbol hay una oración
	public Preprocesador(){
	}

	public Preprocesador(String textooriginal){
		this.textooriginal=textooriginal;
		iniciarPreprocesamiento();
	}

	public static void main(String[] args){
		Preprocesador obj = new Preprocesador("Había una vez un conejito soñador que vivía en una casita en medio del bosque, rodeado de libros y fantasía, pero no tenía amigos. Todos le habían dado de lado porque se pasaba el día contando historias imaginarias sobre hazañas caballerescas, aventuras submarinas y expediciones extraterrestres. Siempre estaba inventando aventuras como si las hubiera vivido de verdad, hasta que sus amigos se cansaron de escucharle y acabó quedándose solo.\nAl principio el conejito se sintió muy triste y empezó a pensar que sus historias eran muy aburridas y por eso nadie las quería escuchar. Pero pese a eso continuó escribiendo.\nLas historias del conejito eran increíbles y le permitían vivir todo tipo de aventuras. Se imaginaba vestido de caballero salvando a inocentes princesas o sintiendo el frío del mar sobre su traje de buzo mientras exploraba las profundidades del océano.");
	}

	static void iniciarPreprocesamiento(){
		//System.out.println(textooriginal);

		String palabratemporal = "";
		int contadordecalidad = 0;
		ArbolBST arboltemporal = new ArbolBST();
		for (int i=0; i<textooriginal.length(); i++){
			String caractertemporal = textooriginal.substring(i,i+1);
			if(caractertemporal.equals(" ")||caractertemporal.equals(";")||caractertemporal.equals(":")||caractertemporal.equals("\n")){ //aqui termina una palabra, hemos generalizado un poco esto
				if(contadordecalidad >3){
					System.out.println(palabratemporal + " - es una palabra util");				
					arboltemporal.insertar(new Node(palabratemporal));//aqui estamos generando el arbol, palabra por palabra(ademas estamos creando un nodo por cada palabra)
				}

				palabratemporal = "";
				contadordecalidad = 0;
				continue;

			}else if(caractertemporal.equals(".")){

				System.out.println("\naqui termino una oración\n");


				//enviamos el arbol temporal a el arreglo global
				arreglodearboles.add(arboltemporal);
				arboltemporal = new ArbolBST();

				palabratemporal = "";
				contadordecalidad = 0;
			}else{
				palabratemporal += caractertemporal;
				contadordecalidad++;
			}
		}

	}
}

