import java.util.ArrayList;
public class Postprocesamiento implements java.io.Serializable{//tiene que se serializable para poder leerse y escribirse
    private String textodudoso="";//este es el texto de dudosa procedencia

    public Postprocesamiento(String textodudoso, Preprocesador preprocesado){
        this.textodudoso= (textodudoso +" .");//es importante poner punto sino la ultima palabra no podria contar
        iniciarPostprocesamiento(preprocesado);
    }

//    public static void main(String[] args) {
//		Preprocesador obj = new Preprocesador("Había una vez un conejito soñador que vivía en una casita en medio del bosque, rodeado de libros y fantasía, pero no tenía amigos. Todos le habían dado de lado porque se pasaba el día contando historias imaginarias sobre hazañas caballerescas, aventuras submarinas y expediciones extraterrestres. Siempre estaba inventando aventuras como si las hubiera vivido de verdad, hasta que sus amigos se cansaron de escucharle y acabó quedándose solo.\nAl principio el conejito se sintió muy triste y empezó a pensar que sus historias eran muy aburridas y por eso nadie las quería escuchar. Pero pese a eso continuó escribiendo.\nLas historias del conejito eran increíbles y le permitían vivir todo tipo de aventuras. Se imaginaba vestido de caballero salvando a inocentes princesas o sintiendo el frío del mar sobre su traje de buzo mientras exploraba las profundidades del océano.");
//        Postprocesamiento obj1 = new Postprocesamiento("Había una vez un conejito soñador que vivía en una casita");
//    }

    public void iniciarPostprocesamiento(Preprocesador preprocesado) {
		String palabratemporal = "";
		int contadordecalidad = 0;
        ArrayList<ArbolBST> arreglodearbolestemporal = preprocesado.getArreglodeArboles();

        for (int i = 0; i < textodudoso.length(); i++) {
            String caractertemporal = textodudoso.substring(i, i + 1);
            if (caractertemporal.equals(" ") || caractertemporal.equals(";") || caractertemporal.equals(":")|| caractertemporal.equals("\n")|| caractertemporal.equals(",")) { // aqui termina una palabra, hemos generalizado un poco esto
                if (contadordecalidad > 3) {
                    System.out.println(palabratemporal + " - es una palabra util");
                    for (int j = 0; j < arreglodearbolestemporal.size(); j++) {

                        arreglodearbolestemporal.get(j).buscar(palabratemporal);// aqui estamos buscando en cada arbol, arbol por arbol
                    }
                }

                palabratemporal = "";
                contadordecalidad = 0;
                continue;

            } else if (caractertemporal.equals(".")) {// si encontramos un . significa que acabo una oración

                if (contadordecalidad > 3) {
                    System.out.println(palabratemporal + " - es una palabra util");
                    for (int j = 0; j < arreglodearbolestemporal.size(); j++) {
                        // System.out.println("entro a el bucle de arboles");

                        arreglodearbolestemporal.get(j).buscar(palabratemporal);// aqui estamos generando el arbol,
                                                                                // palabra por
                    }
                }

                System.out.println("\naqui termino una oración\n");

                // enviamos el arbol temporal a el arreglo global

                palabratemporal = "";
                contadordecalidad = 0;
            } else {// aqui entra si encuentra una letra o otro simbolo que se escapa
                palabratemporal += caractertemporal;
                contadordecalidad++;// el conteo de calidad cuenta cuantas letras tiene la palabra hasta el momento
            }
        }
    }

    public static void informarPlagio(Preprocesador preprocesado) {
        ArrayList<ArbolBST> arraydearbolesttemporal = preprocesado.getArreglodeArboles();
        for (int i = 0; i < arraydearbolesttemporal.size(); i++) {
            System.out.println("la oración " + (i+1) + " tuvo " + arraydearbolesttemporal.get(i).palabrasplagiadas+ "palabras plagiadas");
        }
    }

}
