package cat.urv.clic.android;

import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import android.app.Activity;
import android.os.Bundle;

public class Prova extends Activity {
    public void onCreate(Bundle savedInstanceState) {
    	try {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.llista);
    		
            SAXBuilder builder=new SAXBuilder(false); 
            System.out.println("ENTRARRRRRRR");
            
            InputStream is = getAssets().open("animals.xml");
            Document doc=builder.build(is);
            System.out.println("DESPRESSSSS");
            
            //construyo el arbol en memoria desde el fichero
            // que se lo pasaré por parametro.
            Element raiz=doc.getRootElement();
            //cojo el elemento raiz
            
/*            System.out.println("La liga es de tipo:"+raiz.getAttributeValue("tipo"));
            //todos los hijos que tengan como nombre plantilla
            List equipos=raiz.getChildren("equipo");
            System.out.println("Formada por:"+equipos.size()+" equipos");
            Iterator i = equipos.iterator();
            while (i.hasNext()){
                Element e= (Element)i.next();
                //primer hijo que tenga como nombre club
                Element club =e.getChild("club"); 
                List plantilla=e.getChildren("plantilla"); 
                System.out.println
                              (club.getText()+":"+"valoracion="+
                               club.getAttributeValue("valoracion")+","+
                               "ciudad="+club.getAttributeValue("ciudad")+","+
                               "formada por:"+plantilla.size()+"jugadores");
                 if (e.getChildren("conextranjeros").size()==0)
                	 System.out.println("No tiene extranjeros");
                 else  System.out.println("Tiene extranjeros");
            }*/
            System.out.println("La granja es de :"+raiz.getAttributeValue("nom"));
            List parcela=raiz.getChildren("parcela");
            System.out.println("Formada por:"+parcela.size()+" parceles");
            Iterator i = parcela.iterator();
            while (i.hasNext()){
                Element e= (Element)i.next();
                //primer hijo que tenga como nombre club
               // Element club =e.getChild("club"); 
                  List noms=raiz.getChildren("nom");
                  Iterator ii = noms.iterator();
            	  while (ii.hasNext()){
            	   	 Element ee= (Element)ii.next();
            	   	 System.out.println("Animal :" + ee.getText());
            	  }
            }
            
            
            System.out.println("ACABAAAAAAAA");
         }catch (Exception e){
            e.printStackTrace();
         }
    }
}