import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private static Main ventana;
    private JButton boton0 = new JButton("Abrir archivo");
    private JButton boton1 = new JButton("Preprocesar");
    private JButton boton2 = new JButton("Postprocesar");
    private JButton boton3 = new JButton("Mostrar Resultados");

    private static JPanel panelcheck = new JPanel(new GridLayout(4, 1));

    static ArrayList<Preprocesador> arreglodepreprocesados = new ArrayList<Preprocesador>();
    static String textosospechoso;
    static Postprocesamiento objetopostprocesador;

    /* Definimos variables. */
    private JTextArea textarea1;
    private JScrollPane scrollpane1;

    public Main() {
        /* Configuración del JFrame */
        setLayout(new GridLayout(1, 2));
        setBounds(0, 0, 1080, 600);
        setTitle("Ejemplo 7: JTextArea con JScrollPane");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Cuerpo de mensaje en scrollPane */
        textarea1 = new JTextArea(leerContenido("./Cache/CacheAreadeTexto.txt"));
        textarea1.setLineWrap(true); // Para que salte de línea al llegar al final del ancho del jTextArea
        scrollpane1 = new JScrollPane(textarea1);
        scrollpane1.setBounds(10, 50, 400, 300);


        boton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton0ActionPerformed(evt);
            }
        });
        boton1.addActionListener(new java.awt.event.ActionListener() {
            	public void actionPerformed(ActionEvent e){
                File folder = new File("./TextosOriginales");

                findAllFilesInFolder(folder);// agarra todos los archivos de la biblio

                respaldarCachedeArreglodeProcesados();//rellena el archivo dat en el que estara el cache con los arboles

   				JOptionPane.showMessageDialog(ventana, "Preprocesamiento Terminado");//informa de que el proceso acabó
            }
        });
        boton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textosospechoso = textarea1.getText();
                postprocesar();
                escribirContenido(textarea1.getText(), "./Cache/CacheAreadeTexto.txt");
   				JOptionPane.showMessageDialog(ventana, "Postprocesamiento Terminado");//informa de que el proceso acabó
            }
        });
        boton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarResultados();
   				JOptionPane.showMessageDialog(ventana, "finalizado");//informa de que el proceso acabó
            }
        });

        panelcheck.add(boton0);
        panelcheck.add(boton1);
        panelcheck.add(boton2);
        panelcheck.add(boton3);
        add(panelcheck);
        add(scrollpane1);

        /* Muestro el JFrame */
        setVisible(true);
    }

    private void boton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String ruta = "";
        String temp = "";
        JFileChooser jfile = new JFileChooser();
        FileNameExtensionFilter filtrado = new FileNameExtensionFilter("TXT", "txt");
        jfile.setFileFilter(filtrado);

        int respuesta = jfile.showOpenDialog(this);

        if (respuesta == jfile.APPROVE_OPTION) {
            ruta = jfile.getSelectedFile().getPath();
            if (respuesta == jfile.APPROVE_OPTION) {
                ruta = jfile.getSelectedFile().getPath();
                File doc = new File(ruta);
                Scanner obj = null;
                try {
                    obj = new Scanner(doc);
                } catch (FileNotFoundException ex) {
                    //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println("ERROR");
                }
                
                while (obj.hasNextLine()) {
                    temp += (obj.nextLine() + "\n");
                }
                txtTexto.setText(temp);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String[] args) {

        ventana = new Main();

        // File folder = new File("./TextosOriginales");

        // findAllFilesInFolder(folder);//lee, y preprocesa

        // respaldarCachedeArreglodeProcesados();//para que en las segundas ejecuciónes
        // ya no preprocese los textos originales y demore menos

        //// Nota mental: SI CAMBIAS ALGO (ya sea borrar o agregar)DEBES DE EJECUTAR LAS
        //// LINEAS ANTERIORES O NO FUNCIONCIONARA Y BOTARAR ERROR DE SERIALIZACIÓN O
        //// QUIZA OTRO QUE NI ENTIENDAS

        // postprocesar();

        // mostrarResultados();
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
        ///////// aqui podemos imprimir todos los arboles
        // for (ArbolBST arbolito : temporal.getArreglodeArboles()) {
        // arbolito.imprimirArbol();
        // }
        ////////// 7
        arreglodepreprocesados.add(temporal);// aqui añado el preprocesameinto a el array de estos mismos.

    }

    public static void postprocesar() { // aqui se postprocesaran los que ya se añadierona al array, de hecho aqui se
                                        // iteran lso pres
                                        
        limpiar(textosospechoso);//antes limpiaba dentro de la clase postprocesar pero no es tan optimo pues ese paso se repite demaciado insulsamente
        Postprocesamiento.arregloconindicesdeplagio = new ArrayList<ArrayList<Integer>>();//Para que al hacer dos consultas no se reuse lo anterior
        leerCachedeArreglodePreprocesados();
        for (int i = 0; i < arreglodepreprocesados.size(); i++) {
            postprocesarauxiliar(arreglodepreprocesados.get(i));
            for (ArbolBST arbolito : arreglodepreprocesados.get(i).getArreglodeArboles()) {
                arbolito.imprimirArbol();
            }
        }

    }

    public static void limpiar(String texto){
        texto.replace(",", "");
        texto.replace("¿", "");
        texto.replace("?", "");
        texto.replace("¡", "");
        texto.replace("!", "");
        texto.replace("\"", "");

        String textolimpiotemporal = "";
        String palabratemporal = "";

        int contadordecalidad = 0; 
        for (int i = 0; i < texto.length(); i++) {
            String caractertemporal = texto.substring(i, i + 1);
            if (caractertemporal.equals(" ") || caractertemporal.equals(".")) { // aqui termina una palabra
                if (contadordecalidad > 3) {//es una palabra util
                    textolimpiotemporal += palabratemporal + caractertemporal;// aqui estamos buscando en cada arbol, arbol por arbol
                }

                palabratemporal = "";
                contadordecalidad = 0;
                continue;
            }else {// aqui entra si encuentra una letra o otro simbolo que se escapa
                palabratemporal += caractertemporal;
                contadordecalidad++;// el conteo de calidad cuenta cuantas letras tiene la palabra hasta el momento
            }

        }
        texto = textolimpiotemporal;
    }

    public static void postprocesarauxiliar(Preprocesador temporal) { // aqui vienen todos a terminar de
                                                                      // postprocesarce
        objetopostprocesador = new Postprocesamiento(textosospechoso, temporal);
    }

    public static void mostrarResultados() {
        System.out.println("ESTOS SON LOS RESULTADOS");
        for (int i = 0; i < arreglodepreprocesados.size(); i++) {
            System.out.println("resultado " + i);
            objetopostprocesador.informarPlagio(arreglodepreprocesados.get(i));
        }
        objetopostprocesador.informarPlagiopormiLado();
    }

    public static void respaldarCachedeArreglodeProcesados() {
        String nombreFichero = "./Cache/CachePreprocesados.dat";
        try {
            System.out.println("INGRESAMOS PARA ESCRIBIR :D");

            FileOutputStream ficheroSalida = new FileOutputStream(nombreFichero);// intanciamos objeto capaz de
                                                                                 // abrir
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
        String nombreFichero = "./Cache/CachePreprocesados.dat";
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

    public static void escribirContenido(String contenido, String ruta){
        try{
            PrintWriter fileOut = new PrintWriter(ruta); //sobreescribe
            fileOut.println(contenido);
            fileOut.close();
            System.out.println("Archivo Guardado");
        }catch(FileNotFoundException e){
            System.out.println("error: "+e.getMessage());
        }
    }

    public static String leerContenido(String ruta) {
        String line =  "";
        try {
            Scanner sc = new Scanner(new FileReader(ruta));
            while (sc.hasNextLine()) {
                line += sc.nextLine() + "\n";
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("error: " + e.getMessage());
        }
        return line;
    }

    private javax.swing.JTextArea txtTexto;
}
