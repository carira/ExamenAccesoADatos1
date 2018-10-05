package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GestionDatos {

	
	public GestionDatos() {

	}

	//TODO: Implementa una función para abrir ficheros
	public BufferedReader abrirFichero(FileReader f1) throws FileNotFoundException
	{
		BufferedReader br1 = new BufferedReader(f1);
		
		return br1;
	}
	
	//TODO: Implementa una función para cerrar ficheros
	public void cerrarFichero(FileReader f1, BufferedReader b1) throws IOException
	{
		b1.close();
		f1.close();
	}
	
	
	public boolean compararContenido (String fichero1, String fichero2) throws IOException
	{
		FileReader f1 = new FileReader(fichero1);
		FileReader f2 = new FileReader(fichero2);
		
		BufferedReader br1 = abrirFichero(f1);
		BufferedReader br2 = abrirFichero(f2);
		
		boolean fin = true;
		String ficheroUno="";
		String ficheroDos="";
		
		while (fin)
		{
			ficheroUno=br1.readLine();
			ficheroDos=br2.readLine();
			
			if (ficheroUno==null || ficheroDos==null)
			{
				fin=false;
				if (ficheroUno==null && ficheroDos!=null)
				{
					cerrarFichero(f1,br1);
					cerrarFichero(f2,br2);
					return false;
				}
				if (ficheroUno!=null && ficheroDos==null)
				{
					cerrarFichero(f1,br1);
					cerrarFichero(f2,br2);
					return false;
				}
				cerrarFichero(f1,br1);
				cerrarFichero(f2,br2);
			}
			
			if (fin)
			{
				if (ficheroUno.compareTo(ficheroDos)!=0)
				{
					cerrarFichero(f1,br1);
					cerrarFichero(f2,br2);
					return false;
				}
			}
		}
			
		cerrarFichero(f1,br1);
		cerrarFichero(f2,br2);
		
		return true;
	}
	
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) throws IOException{
		FileReader f1 = new FileReader(fichero1);
		BufferedReader br1 = abrirFichero(f1);
		
		String linea = "";
		int lineaActual=0;
		int numLinea=0;
		
		while (linea!=null)
		{
			linea=br1.readLine();
			if (linea==null)
			{
				cerrarFichero(f1,br1);
				return numLinea;
			}
			
			lineaActual++;
			if (linea.contains(palabra))
			{
				numLinea=lineaActual;
				if (primera_aparicion==true)
				{
					cerrarFichero(f1,br1);
					return numLinea;
				}
			}
		}
		
	return 0;
}
	
	public String buscarPalabras (String fichero1, String longitud) throws IOException, NullPointerException{
		FileReader f1 = new FileReader(fichero1);
		BufferedReader br1 = abrirFichero(f1);
		int longi = Integer.parseInt(longitud);
		String linea = "", palabras="ninguna";
		int length=0,cont=0;
		
		while (linea!=null)
		{
			linea=br1.readLine();
			
			
			
			
			if (linea==null)
			{
				
				cerrarFichero(f1,br1);
				return palabras;
			}
			
			
			length=linea.length();
			cont++;
			if (longi==length)
			{
			if (cont!=1) {
				palabras=palabras+"\n"+linea;
			}
			else {
				palabras="\n"+linea;
			}
					
				
			}
			
		}
		
		
		
	cerrarFichero(f1,br1);	
	return palabras;
}
	
	

	public int guardarLibro(Libro libro) throws FileNotFoundException, IOException
	{
		String path="ficheros\\";
		path+=String.valueOf(libro.getId());
		File f1=new File(path);

		if (!f1.exists())
		{
			ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(f1));
			oos.writeObject(libro);
			oos.close();
		}
		else
		{
			return 0;
		}

		return 1;
	}
	
	public Libro recuperarLibro(String id) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		File f1=new File("ficheros\\"+id);
		
		if (f1.exists())
		{
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f1));
			Libro l1=(Libro) ois.readObject();
			ois.close();
			return l1;
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<Libro> recuperarTodos() throws FileNotFoundException, ClassNotFoundException, IOException
	{
		File f1 = new File("ficheros");
		String ficheros[] = f1.list();
		ArrayList <Libro> libros = new ArrayList<Libro>();
		
		for (int i=0; i<ficheros.length; i++)
		{
			libros.add(recuperarLibro(ficheros[i]));
		}
		
		return libros;
	}
}