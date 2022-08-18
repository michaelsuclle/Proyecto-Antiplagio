import java.text.Collator;
public class ArbolBST implements java.io.Serializable{//tiene que se serializable para poder leerse y escribirse 
    static Collator comparador = Collator.getInstance();//esta linea obtiene una instancia de la clase de collator(muy parecido a compareTo)
    private Node noderoot;
    int numerodenodos;
    int palabrasplagiadas = 0;
    int nodepalabras=0;

//    public static void main(String[] args) {
//	    comparador.setStrength(Collator.SECONDARY);//SECONDARY es una configuración para que comparador ignore ACENTOS, MAYUSCULAS y caracteres especiales(a diferencia de compareTo)
//        ArbolBST arbol = new ArbolBST();
//        arbol.insertar(new Node("hola"));
//        arbol.insertar(new Node("amor"));
//        arbol.insertar(new Node("rosa"));
//        arbol.insertar(new Node("szzs"));
//        arbol.buscar("szZs");
//    }


    public ArbolBST() {
    }

    public Node getNoderoot (){
        return noderoot;
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
        comparador.setStrength(Collator.SECONDARY);
        int indicecomparador = comparador.compare(nodoacomparar.valor, elemento.valor);// aqui comparamos(-1 si el
                                                                                       // izquierdo va antes y es 1 si
                                                                                       // el derecho va antes)
        if (indicecomparador < 0) {
            //System.out.println(elemento.valor + " Debe de colocarse a la derecha de " + nodoacomparar.valor);
            if (nodoacomparar.nododerecho == null) { // aqui vemos si el nodo actual de comparación no tiene hijo
                                                     // derecho
                nodoacomparar.nododerecho = elemento;
            } else {
                integrarhijo(nodoacomparar.nododerecho, elemento);// si si tiene hijo entonces seguimos buscando
                return;
            }
        } else {// aqui entra si indicecomparador es mayor a 0, o si es 0
            //System.out.println(elemento.valor + " Debe de colocarse a la izquierda de " + nodoacomparar.valor);
            if (nodoacomparar.nodoizquierdo == null) { // aqui vemos si el nodo actual de comparación no tiene hijo
                                                       // izquierdo
                nodoacomparar.nodoizquierdo = elemento;
            } else {
                integrarhijo(nodoacomparar.nodoizquierdo, elemento);// si si tiene hijo entonces seguimos buscando
                return;
            }
        }
    }

    public int buscar(String elementoabuscar){ // String porque sabemos que trabajaremos con estos
        //System.out.println("ENTRAMOS A BUSQUEDA");
        if (noderoot == null||noderoot.valor == null) {
            //System.out.println("no hay nodos");
            return 0;
        } else {
            //System.out.println(" vs buscando en  ");
            return buscarauxiliar(noderoot, elementoabuscar);
        }
    }

    public int buscarauxiliar(Node elementoreferencia, String elementoabuscar) {
        //System.out.println("entramos a buscar");
        comparador.setStrength(Collator.SECONDARY);
        int indicecomparador = comparador.compare(elementoreferencia.valor, elementoabuscar);// aqui comparamos(-1 si el izquierdo va antes y es 1 si el derecho va antes)
        if (indicecomparador == 0) {// verificamos si coincide
            System.out.println("Las palabras " + elementoabuscar + " son iguales - MATCH");
            palabrasplagiadas++;
            return 1;
        } else {
            if (indicecomparador < 0) {//
                if (elementoreferencia.nododerecho == null) {
                    //System.out.println("palabra no encontrada");
                    return 0;
                }
                //System.out.println(elementoabuscar + " Debe estar a la derecha de " + elementoreferencia.valor);
                return buscarauxiliar(elementoreferencia.nododerecho, elementoabuscar);
            } else {
                if (elementoreferencia.nodoizquierdo == null) {
                    //System.out.println("palabra no encontrada");
                    return 0;
                }
                //System.out.println(elementoabuscar + " Debe estar a la izquierda de " + elementoreferencia.valor);
                return buscarauxiliar(elementoreferencia.nodoizquierdo, elementoabuscar);
            }
        }
    }
    public void imprimirArbol() {
      impresorAyudante(this.noderoot, "", true);
    }
    private void impresorAyudante(Node currPtr, String indent, boolean last) {
        // para imprimir la estructura en pantalla
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }
            System.out.println("(VALOR = "+currPtr.valor+")");

            impresorAyudante(currPtr.nodoizquierdo, indent, false);
            impresorAyudante(currPtr.nododerecho, indent, true);
        }
    }
}
