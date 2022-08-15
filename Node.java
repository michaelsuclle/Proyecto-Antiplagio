public class Node<T> extends requerimientosNode { // si heredamos una clase generica podemos tener atributos de caracter con flexibilidad/mutabilidad de tipo
    public Node( T valor ) {
        this.valor = valor;
    }

    public Node() {

    }
}

class requerimientosNode<T> {
    T valor;
    Node nododerecho;
    Node nodoizquierdo;
}
