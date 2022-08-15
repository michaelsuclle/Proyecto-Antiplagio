import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Preprocesador> preprocesados = new ArrayList<Preprocesador>();
		Preprocesador obj = new Preprocesador("Había una vez un conejito soñador que vivía en una casita en medio del bosque, rodeado de libros y fantasía, pero no tenía amigos. Todos le habían dado de lado porque se pasaba el día contando historias imaginarias sobre hazañas caballerescas, aventuras submarinas y expediciones extraterrestres. Siempre estaba inventando aventuras como si las hubiera vivido de verdad, hasta que sus amigos se cansaron de escucharle y acabó quedándose solo.\nAl principio el conejito se sintió muy triste y empezó a pensar que sus historias eran muy aburridas y por eso nadie las quería escuchar. Pero pese a eso continuó escribiendo.\nLas historias del conejito eran increíbles y le permitían vivir todo tipo de aventuras. Se imaginaba vestido de caballero salvando a inocentes princesas o sintiendo el frío del mar sobre su traje de buzo mientras exploraba las profundidades del océano.");
        Postprocesamiento obj1 = new Postprocesamiento("Había una vez un conejito soñador que vivía en una casita.fantasía, pero no tenía amigos. Todos le habían dado de lado porque se pasaba el inocentes princesas o sintiendo el frío del mar sobre su traje de buzo mientras exploraba", obj);
        obj1.informarPlagio(obj);

		File folder = new File("./TextosOriginales");
		findAllFilesInFolder(folder);
    }

	public static void findAllFilesInFolder(File folder) {
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println(file.getName());
			} else {
				findAllFilesInFolder(file);
			}
		}
	}
}


