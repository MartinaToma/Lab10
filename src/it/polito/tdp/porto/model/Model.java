package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author,DefaultEdge> grafo;
	private PortoDAO dao;
	private Map<Integer,Author> tutti;
	private List<Author> listaCompleta;
	private List<CoAutori> presenti;
 	//private Map<Author,Author> backVisit;
	
	public Model() {
		grafo=new SimpleGraph<>(DefaultEdge.class);
		tutti=new HashMap<Integer,Author>();
		//backVisit=new HashMap<Author,Author>();
		
	}
	
	public void creaGrafo() {
		dao=new PortoDAO();
		listaCompleta=dao.loadAllAuthors(tutti);
		Graphs.addAllVertices(grafo, listaCompleta);
		
		System.out.println("Vertici: "+grafo.vertexSet().size());
		presenti=dao.getListaAuthors(tutti);
		for(CoAutori cc: presenti) {
			Graphs.addEdgeWithVertices(grafo, cc.getA1(), cc.getA2());
		}
		System.out.println("Archi: "+grafo.edgeSet().size());
	}

}
