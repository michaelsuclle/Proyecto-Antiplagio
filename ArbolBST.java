import java.text.Collator;
public class ArbolBST{ 
    static Collator comparador = Collator.getInstance();//esta linea obtiene una instancia de la clase de collator(muy parecido a compareTo)
    Node noderoot;
    int numerodenodos;
    int palabrasplagiadas = 0;

    public static void main(String[] args) {
	    comparador.setStrength(Collator.SECONDARY);//SECONDARY es una configuración para que comparador ignore ACENTOS, MAYUSCULAS y caracteres especiales(a diferencia de compareTo)
        ArbolBST arbol = new ArbolBST();
        arbol.insertar(new Node("hola"));
        arbol.insertar(new Node("amor"));
        arbol.insertar(new Node("rosa"));
        arbol.insertar(new Node("szzs"));
        arbol.buscar("szZs");
    }

    public ArbolBST() {

    }

    public void insertar(Node elemento) {//para insertar nodos
        if (numerodenodos == 0) {// si no hay nodos el que vamos a añadir debe ser la raiz
            numerodenodos++;
            noderoot = elemento;
        } else {
            integrarhijo(noderoot, elemento);
        }
    }

    // como este nodo sera String, usaremos el compareitor para eso estamos
    // importando una libreria
    public void integrarhijo(Node nodoacomparar, Node elemento) {
        int indicecomparador = comparador.compare(nodoacomparar.valor, elemento.valor);// aqui comparamos(-1 si el
                                                                                       // izquierdo va antes y es 1 si
                                                                                       // el derecho va antes)
        if (indicecomparador < 0) {
            System.out.println(nodoacomparar.valor + " y " + elemento.valor + " - El primero va antes");
            if (nodoacomparar.nododerecho == null) { // aqui vemos si el nodo actual de comparación no tiene hijo
                                                     // derecho
                nodoacomparar.nododerecho = elemento;
            } else {
                integrarhijo(nodoacomparar.nododerecho, elemento);// si si tiene hijo entonces seguimos buscando
                return;
            }
        } else {// aqui entra si indicecomparador es mayor a 0, o si es 0
            System.out.println(
                    nodoacomparar.valor + " y " + elemento.valor + " - El segundo va antes " + indicecomparador);
            if (nodoacomparar.nodoizquierdo == null) { // aqui vemos si el nodo actual de comparación no tiene hijo
                                                       // izquierdo
                nodoacomparar.nodoizquierdo = elemento;
            } else {
                integrarhijo(nodoacomparar.nodoizquierdo, elemento);// si si tiene hijo entonces seguimos buscando
                return;
            }
        }
    }

    public void buscar(String elementoabuscar) { // String porque sabemos que trabajaremos con estos
        if (noderoot == null) {
            System.out.println("no hay nodos");
            return;
        } else {
            buscarauxiliar(noderoot, elementoabuscar);
        }
    }

    public void buscarauxiliar(Node elementoreferencia, String elementoabuscar) {
        int indicecomparador = comparador.compare(elementoreferencia.valor, elementoabuscar);// aqui comparamos(-1 si el
                                                                                             // izquierdo va antes y es
                                                                                             // 1 si el derecho va
                                                                                             // antes)
        if (indicecomparador == 0) {// verificamos si coincide
            System.out.println("Las palabras " + elementoabuscar + " son iguales");
            palabrasplagiadas++;
            return;
        } else {
            if (indicecomparador < 0) {//
                if (elementoreferencia.nododerecho == null) {
                    System.out.println("palabra no encontrada");
                    return;
                }
                System.out.println("La primera cadena va antes " + indicecomparador);
                buscarauxiliar(elementoreferencia.nododerecho, elementoabuscar);
                return;
            } else {
                if (elementoreferencia.nodoizquierdo == null) {
                    System.out.println("palabra no encontrada");
                    return;
                }
                System.out.println("La primera cadena va despues " + indicecomparador);
                buscarauxiliar(elementoreferencia.nodoizquierdo, elementoabuscar);
                return;
            }
        }
    }

    // private static agregar

}
