package me.ashy146.godWeapons.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class CraftedItemsDatabase {
	
	
	
	private final Connection connection;
	
	public CraftedItemsDatabase(String path) throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
		
		try(Statement statement = connection.createStatement()){
		statement.execute("""
				CREATE TABLE IF NOT EXISTS weapons (
				weapon TEXT PRIMARY KEY,
				uuid TEXT NOT NULL,
				hasCrafted BOOLEAN NOT NULL DEFAULT false)
		""");
		}
	}
	
	public void closeConnection() throws SQLException {
		if(connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	public void addWeapon(UUID playerID, String godWeapon) {
		try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO weapons (weapon, uuid) VALUES (?, ?)")){
			preparedStatement.setString(1, godWeapon);
			preparedStatement.setString(2, playerID.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasWeaponBeenCrafted(String godWeapon) {
		try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM weapons WHERE weapon = ?")){
			preparedStatement.setString(1, godWeapon);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
