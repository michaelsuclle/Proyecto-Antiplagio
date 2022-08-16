public class Node implements java.io.Serializable{ // si heredamos una clase generica podemos tener atributos de caracter con flexibilidad/mutabilidad de tipo
    String valor;
    Node nododerecho;
    Node nodoizquierdo;
    public Node( String valor ) {
        this.valor = valor;
    }

    public Node() {

    }
}

