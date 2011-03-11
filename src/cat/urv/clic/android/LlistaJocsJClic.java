package cat.urv.clic.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

//import cat.urv.clic.android.GestioFitxers;

public class LlistaJocsJClic extends IniciAplicacio {
	
	private ArrayAdapter<String> adp; 
	private Intent intent = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.llistajocsjclic);
	        // Llista
	        final ListView list = (ListView) findViewById(R.id.list_jocs);
	        	                
	        llegirFitxerJocsXML("jocs.xml", false, this.llistaJocs,this.llistaNoDescarregats);
	        
	        
	        	        
	        adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.llistaNoDescarregats); 
	        list.setAdapter(adp);       
	   		
	        // Clic de la llista
	        intent = new Intent(this, DescripcioJoc.class);	
	        OnItemClickListener onClic = new OnItemClickListener(){
	        						public void onItemClick(AdapterView<?> arg0, View v, int i, long id) {
	        							String str = list.getItemAtPosition(i).toString();
	        					        							
	        							DescripcioJoc.name = str;
	        							startActivity(intent);	        		
	        						}};
	        list.setOnItemClickListener(onClic);        
	        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
       
}

