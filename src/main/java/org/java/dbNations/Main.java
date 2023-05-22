package org.java.dbNations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
public static void main(String[] args) throws Exception {
	
		String url = "jdbc:mysql://localhost:3306/nations";
		String user = "root";
		String password = "code";
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
    
			String sql = " SELECT * FROM countries "
					+ " JOIN regions "
					+ " ON countries.region_id  = regions.region_id "
					+ " JOIN continents "
					+ " ON regions.continent_id = continents.continent_id "
					+ " ORDER BY countries.name ASC ";
			
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				
				try (ResultSet rs = ps.executeQuery()) {
					
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String name = rs.getString(2);
						final int area = rs.getInt(3);
						final String regionName = rs.getString(9);
						final String continentName = rs.getString(12);
						
						System.out.println(name + " - " + id + " - " 
								+ area + " - " + regionName + " - " + continentName);
					}
				}				
			} catch (SQLException ex) {
				System.err.println("Query not well formed");
			}
		
		} catch (SQLException ex) {
			System.err.println("Error during connection to db");
			}

	}

}
