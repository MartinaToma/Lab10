package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private class EdgeTrasversalListener implements TraversalListener<Author,DefaultEdge>{

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			
			
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			
			
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
			
			
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<Author> arg0) {
			
			
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<Author> arg0) {
			
			
		}
		
	}
	
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
	public List<Author> autori(){
		return this.listaCompleta;
	}
	
	

	public List<Author> listaVicini(Author a){
		
		
		return Graphs.neighborListOf(grafo, a);
	}
	
	public List<Paper> autoriRaggiungibili(Author a1,Author a2){
		List<Paper> paper=new ArrayList<Paper>();
		
		GraphIterator<Author,DefaultEdge> it=new DepthFirstIterator<>(this.grafo,a1);
		it.addTraversalListener(new Model.EdgeTrasversalListener());
		
		return paper;
	}
}
