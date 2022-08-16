import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main  {
    static ArrayList<Preprocesador> arreglodepreprocesados = new ArrayList<Preprocesador>();
    static String textosospechoso = "venia del armario, ahora el goteo era más claro conforme se acercaba, al abrir la puerta, pudo ver a su perro colgado y degollado goteando sangre,";
    static Postprocesamiento objetopostprocesador;
    public static void main(String[] args) {


        File folder = new File("./TextosOriginales");

        findAllFilesInFolder(folder);//lee, y preprocesa


        respaldarCachedeArreglodeProcesados();//para que en las segundas ejecuciónes ya no preprocese los textos originales y demore menos

        //Nota mental: SI CAMBIAS ALGO (ya sea borrar o agregar)DEBES DE EJECUTAR LAS LINEAS ANTERIORES O NO FUNCIONCIONARA Y BOTARAR ERROR DE SERIALIZACIÓN O QUIZA OTRO QUE NI ENTIENDAS


        postprocesar();



        mostrarResultados();
    }

    public static void findAllFilesInFolder(File folder) { // esta funcion lee los archivos que tienes en tu carpeta
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());
                leerArchivo(file.getName());

            } else {
                findAllFilesInFolder(file);
            }
        }
    }

    public static void leerArchivo(String nombredearchivo) {
        String contenido = "";
        try (FileInputStream fis = new FileInputStream("./TextosOriginales/" + nombredearchivo);
                InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1"); // lo puse con iso porque con utf-8 me
                                                                                  // salian signos de interrogación en
                                                                                  // letras con acento
                BufferedReader reader = new BufferedReader(isr)) {

            String str;
            while ((str = reader.readLine()) != null) {// iteramos cada linea y la guardamos en una lea temporal
                contenido += str;
            }
            System.out.println(contenido);
            preprocesar(contenido);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void preprocesar(String contenido) {
        Preprocesador temporal = new Preprocesador(contenido);// aqui preproceso el contenido
        /////////aqui podemos imprimir todos los arboles
        //for (ArbolBST arbolito : temporal.getArreglodeArboles()) {
        //        arbolito.imprimirArbol();
        //    }
        //////////7
        arreglodepreprocesados.add(temporal);// aqui añado el preprocesameinto a el array de estos mismos.

    }

    public static void postprocesar(){ // aqui se postprocesaran los que ya se añadierona al array, de hecho aqui se iteran lso pres
        leerCachedeArreglodePreprocesados();
        for (int i = 0; i < arreglodepreprocesados.size(); i++) {
            postprocesarauxiliar(arreglodepreprocesados.get(i));
            for (ArbolBST arbolito : arreglodepreprocesados.get(i).getArreglodeArboles()) {
                arbolito.imprimirArbol();
            }
        }

    }

    public static void postprocesarauxiliar(Preprocesador temporal) { // aqui vienen todos a terminar de postprocesarce
        objetopostprocesador = new Postprocesamiento(textosospechoso, temporal);
    }

    public static void mostrarResultados() {
        System.out.println("ESTOS SON LOS RESULTADOS");
        for (int i = 0; i < arreglodepreprocesados.size(); i++) {
            System.out.println("resultado " + i);
            objetopostprocesador.informarPlagio(arreglodepreprocesados.get(i));
        }
    }

    public static void respaldarCachedeArreglodeProcesados() {
        String nombreFichero = "./CachePreprocesados/CachePreprocesados.dat";
        try {
            System.out.println("INGRESAMOS PARA ESCRIBIR :D");

            FileOutputStream ficheroSalida = new FileOutputStream(nombreFichero);// intanciamos objeto capaz de abrir
                                                                                 // ficheros
            ObjectOutputStream objetoSalida = new ObjectOutputStream(ficheroSalida);// instanciamos objeto capaz de
                                                                                    // escribir

            objetoSalida.writeObject(arreglodepreprocesados);
            objetoSalida.close();
            System.out.println("TERMINó DE ESCRIBIR :D");
        } catch (FileNotFoundException e) {
            System.out.println("¡El fichero no existe!");
        } catch (IOException e) {
            System.out.println("error IOEX: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error Exce: " + e.getMessage());
        }

    }

    public static void leerCachedeArreglodePreprocesados() {
        String nombreFichero = "./CachePreprocesados/CachePreprocesados.dat";
        try {
            FileInputStream ficheroEntrada = new FileInputStream(nombreFichero);// abre archivo
            ObjectInputStream objetoEntrada = new ObjectInputStream(ficheroEntrada);// lee objetos
            arreglodepreprocesados = (ArrayList<Preprocesador>) objetoEntrada.readObject(); // hacemos casting
            objetoEntrada.close();// aqui viene el cierre del objeto
            ficheroEntrada.close();
            System.out.println("Archivo leido satisfatoriamente :D");
        } catch (FileNotFoundException e) {
            System.out.println("¡El fichero no existe!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
