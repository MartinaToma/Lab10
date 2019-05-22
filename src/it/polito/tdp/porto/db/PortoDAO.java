package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAutori;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> loadAllAuthors(Map<Integer, Author> autori) {
		final String sql = "SELECT * FROM author ";
		List<Author> result=new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				if(autori.get(rs.getInt("id"))==null) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				result.add(autore);
				autori.put(autore.getId(), autore);				
				}
				else {
					result.add(autori.get(rs.getInt("id")));
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<CoAutori> getListaAuthors(Map<Integer, Author> tutti) {
		final String sql="SELECT DISTINCT c1.authorid, c2.authorid " + 
				"FROM creator AS c1, creator AS c2 " + 
				"WHERE c1.authorid>c2.authorid  AND c1.eprintid=c2.eprintid ";
		List<CoAutori> co=new ArrayList<CoAutori>();
		
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a1=tutti.get(rs.getInt("c1.authorid"));
				Author a2=tutti.get(rs.getInt("c2.authorid"));
				
				
				if(a1==null||a2==null) {
					throw new RuntimeException("Problema in getAuthors");
				}
				CoAutori cc=new CoAutori(a1,a2);
							
				co.add(cc);

			}

			conn.close();
			return co;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
}