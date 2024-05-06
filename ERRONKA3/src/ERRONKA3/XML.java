package ERRONKA3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ERRONKA3.Jokalaria;

public class XML {
	public void denboraldiaXMLGeneratu(ArrayList<Denboraldia> denboraldiak, ArrayList<Taldea> taldeak) {
		String [] escudo = new String[] {"gernika.jpg","durango.png","arratiko zekorrak.jpg","getxo.jpg","eibar.jpg","arrasate.png"};
		try (FileWriter writer = new FileWriter("federazioa.xml")) {
            // Escribir la cabecera del XML
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<federazioa>\n");
            writer.write("\t<usuarios>\n");
            writer.write("\t</usuarios>\n");
            
            
            writer.write("\t<denboraldiak>\n");
            for(Denboraldia denboraldia : denboraldiak) {
            	writer.write("\t\t<denboraldia>\n");
                writer.write("\t\t\t<zenbakia>" + denboraldia.getHasierako_data() + "</zenbakia>\n");
                writer.write("\t\t\t<taldeak>\n");
                for(Taldea taldea : taldeak) {
                	writer.write("\t\t\t\t<taldea>\n");
                   	writer.write("\t\t\t\t\t<izena>" + taldea.getTalde_izena() + "</izena>\n");
                   	if (taldea.getTalde_izena().contains("R. T.")) writer.write("\t\t\t\t\t<escudo>argazkiak/" + taldea.getTalde_izena().substring(0, taldea.getTalde_izena().length()-6).trim() + ".png</escudo>\n");
                   	writer.write("\t\t\t\t\t<jokalariak>\n");
                   	for(Jokalaria jokalaria : taldea.getJokalariak()) {
                   		if(jokalaria.getTaldea().getTalde_izena().equals(taldea.getTalde_izena())) {
                   			writer.write("\t\t\t\t\t\t<jokalaria>\n");
                   			writer.write("\t\t\t\t\t\t\t<izena>" + jokalaria.getIzena() + "</izena>\n");
                   			writer.write("\t\t\t\t\t\t\t<posizioa>" + jokalaria.getPosizioa() + "</posizioa>\n");
                   			writer.write("\t\t\t\t\t\t\t<dorsala>" + jokalaria.getDorsala() + "</dorsala>\n");
                   			writer.write("\t\t\t\t\t\t</jokalaria>\n");
                   		}
                   	}
                   	writer.write("\t\t\t\t\t</jokalariak>\n");
                    writer.write("\t\t\t\t</taldea>\n");
                }
                writer.write("\t\t\t</taldeak>\n");
                writer.write("\t\t\t<jardunaldiak>\n");
                int i = 1;
                for(Jardunaldia jardunaldia : denboraldia.getJardunaldiak()) {
                	writer.write("\t\t\t\t<jardunaldia>\n");
                	writer.write("\t\t\t\t\t<zenbakia>" + i + "</zenbakia>\n");
                	writer.write("\t\t\t\t\t<partiduak>\n");
                	PartiduaDAO partiduaDao = new PartiduaDAO();
                	for(Partidua partidua : jardunaldia.getPartiduak()) {
                		writer.write("\t\t\t\t\t\t<partidua>\n");
                		writer.write("\t\t\t\t\t\t\t<etxekoa>" + partidua.getEtxeko_talde().getTalde_izena() + "</etxekoa>\n");
                		writer.write("\t\t\t\t\t\t\t<kanpokoa>" + partidua.getKanpoko_talde().getTalde_izena() + "</kanpokoa>\n");
                		boolean amaituta = partiduaDao.partiduaAmaitutaGaldetu(partidua);
                		if(amaituta == true) {
                			writer.write("\t\t\t\t\t\t\t<etxeko_golak>" + partidua.getEtxekoGolak() + "</etxeko_golak>\n");
                    		writer.write("\t\t\t\t\t\t\t<kanpoko_golak>" + partidua.getKanpokoGolak() + "</kanpoko_golak>\n");
                		}else {
                			writer.write("\t\t\t\t\t\t\t<etxeko_golak></etxeko_golak>\n");
                    		writer.write("\t\t\t\t\t\t\t<kanpoko_golak></kanpoko_golak>\n");
                		}
                		writer.write("\t\t\t\t\t\t</partidua>\n");
                	}
                	partiduaDao.deskonektatu();
                	writer.write("\t\t\t\t\t</partiduak>\n");
                    writer.write("\t\t\t\t</jardunaldia>\n");
                    i++;
                }
                writer.write("\t\t\t</jardunaldiak>\n");
                writer.write("\t\t</denboraldia>\n");
            }
            writer.write("\t</denboraldiak>\n");
            
            // Cerrar el elemento raíz y el archivo
            writer.write("</federazioa>\n");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
