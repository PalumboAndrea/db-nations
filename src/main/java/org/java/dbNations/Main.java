package org.java.dbNations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
	
public static void main(String[] args) throws Exception {
	
		String url = "jdbc:mysql://localhost:3306/nations";
		String user = "root";
		String password = "code";
		
		try (Scanner sc = new Scanner(System.in);
			Connection con = DriverManager.getConnection(url, user, password)){
    
			String sql = " SELECT * FROM countries "
					+ " JOIN regions "
					+ " ON countries.region_id  = regions.region_id "
					+ " JOIN continents "
					+ " ON regions.continent_id = continents.continent_id "
					+ " WHERE countries.name LIKE ?"
					+ " ORDER BY countries.name ASC ";
			
			System.out.println("Esegui una ricerca:");
			String userSearch = sc.nextLine();
			
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setString(1, "%" + userSearch + "%");
				
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
						System.out.println("\nSeleziona un ID tra quelli proposti sopra");
						int userSearchId = sc.nextInt();
						sc.nextLine();
					
						sql = " SELECT DISTINCT languages.`language`, countries.name, countries.country_id, country_stats.population, country_stats.`year`, country_stats.gdp FROM countries "
								+ " JOIN regions "
								+ " ON countries.region_id  = regions.region_id "
								+ " JOIN continents "
								+ " ON regions.continent_id = continents.continent_id "
								+ " JOIN country_languages "
								+ " ON country_languages.country_id = countries.country_id "
								+ " JOIN languages "
								+ " ON languages.language_id = country_languages.language_id"
								+ " JOIN country_stats "
								+ " ON country_stats.country_id = countries.country_id"
								+ " WHERE countries.country_id LIKE ? "
								+ " ORDER BY country_stats.`year` ASC ";
						
						try (PreparedStatement ps1 = con.prepareStatement(sql)) {
							
							ps1.setLong(1, userSearchId);

							try (ResultSet rs1 = ps1.executeQuery()) {
								
								HashSet<String> languages = new HashSet<String>();
								
								String language;
								String name = null;
								int population = 0;
								int year = 0;
								Long gdp = null;
							
								while(rs1.next()) {
									
									language = rs1.getString(1);
									languages.add(language);
									name = rs1.getString(2);
									population = rs1.getInt(4);
									year = rs1.getInt(5);
									gdp = rs1.getLong(6);
								}
								
								System.out.println("\nDetalis for country: " + name + "\n"
										+ "Languages: " + languages + "\n" 
										+ "Most recent stats:" + "\n" 
										+ "Year: " + year + "\n"
										+ "Population: " + population + "\n"
										+ "GDP: " + gdp + "\n");
								
								}
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
