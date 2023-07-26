package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
	
	Connection con;
1
	
	public App() {
		con = null;
	}


// Question 1
	public void establishCon() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Provide the IP address: ");
		String ip = input.nextLine();
		System.out.println("Provide database to connect to: ");
		String database = input.nextLine();
		System.out.println("Provide username: ");
		String username = input.nextLine();
		System.out.println("Provide password: ");
		String password = input.nextLine();
		
		try {
			Class.forName("org.postgresql.Driver");
			
			con = DriverManager.getConnection("jdbc:postgresql://" +ip + ":5432/" +database, username, password);
			System.out.println("Connection established!");
			con.setAutoCommit(false);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

// Question 2
	public void commitTransaction() {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Question 3
	public void abortTransaction() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Question 4
	public void showLabs() {
		System.out.println(" |Lab Code 		| Lab Title 	|Lab Sector");
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT l.lab_code, l.lab_title, s.sector_title "
					+ " FROM \"Lab\" l "
					+ " JOIN \"Sector\" s "
					+ " ON l.sector_code = s.sector_code "
					+ " ORDER BY l.lab_code ");
			
			ResultSet rs = ps.executeQuery();
			
			while( rs.next() ) {
				System.out.println(" |" + rs.getInt(1) + " |" + rs.getString(2) + " |" + rs.getString(3) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Question 5
	public void showFields() {
		System.out.println(" |Field Code 	| Field Title");
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT code, title"
					+ " FROM \"Field\"");
			
			ResultSet rs = ps.executeQuery();
			
			while( rs.next() ) {
				System.out.println(" |" + rs.getString(1) + " |" + rs.getString(2) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Question 6
	public void renameLab() {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose lab code..");
		int code = input.nextInt();
		System.out.println("What should the new name be..?");
		String newName = input.nextLine();
		
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE \"Lab\" "
					+ " SET lab_title = ?\n"
					+ " WHERE lab_code = ?\n");
			
			ps.setString(1, newName);
			ps.setInt(2, code);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Question 7
	public void renameField() {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose field code..");
		String code = input.nextLine();
		System.out.println("What should the new name be..?");
		String newNAme = input.nextLine();
		
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE \"Field\" "
					+ " SET title = ?\n "
					+ " WHERE code = ?\n");
			
			ps.setString(1, newNAme);
			ps.setString(2, code);
			ps.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}

// Question 8
	public void insertGrades() {
		Scanner input = new Scanner(System.in);
		System.out.println("Insert course code..");
		String code = input.nextLine();
		System.out.println("What should the new grade be?");
		int newGrade = input.nextInt();
		
		try {
			Statement stmt = con.createStatement();
		
			String preview = ("SELECT st.amka, st.surname, st.name "
					+ " FROM \"Student\" st "
					+ " JOIN \"Register\" reg "
					+ " ON st.amka = reg.amka"
					+ " WHERE reg.register_status = 'approved' "
					+ "   AND reg.course_code =  " +code
					+ " GROUP BY st.amka "
					+ " ORDER BY st.amka ");
			
			ResultSet previewRS = stmt.executeQuery(preview);
			
			int amkaPr = previewRS.getInt(1);
			String surnamePr = previewRS.getString(2);
			String namePr = previewRS.getString(3);
			
			System.out.println("Amka: " + amkaPr+ " ");
			System.out.println("Surname: " + surnamePr+ " ");
			System.out.println("Name: " +namePr+ " ");
			
			System.out.println("Select a student's amka to update..");
			int amka = input.nextInt();
			
			String hasLab = ("SELECT reg.course_code "
					+ " FROM \"Register\" reg "
					+ " JOIN \"Course\" cs "
					+ " ON cs.course_code = reg.course_code "
					+ " WHERE reg.register_status = 'approved' "
					+ "   AND cs.lab_hours > 0 and cs.course_code = " +code);
			
			ResultSet hasLabRS = stmt.executeQuery(hasLab);
			
			String courseCode = hasLabRS.getString(1);
			if( courseCode.isEmpty() ) {
				System.out.println("Selected course does not have a lab!");
				
				PreparedStatement ps = con.prepareStatement("UPDATE \"Register\" "
						+ " SET exam_grade = ?"
						+ " WHERE course_code = ?"
						+ "    AND amka = ?");
				
				ps.setInt(1, newGrade);
				ps.setString(2, code);
				ps.setInt(3, amka);
				ps.executeUpdate();
			} else {
				System.out.println("Selected course appears to have a lab!");
				System.out.println("What should the new lab grade be?");
				int newLabGrade = input.nextInt();
				
				PreparedStatement ps = con.prepareStatement("UPDATE \"Register\" "
						+ " SET exam_grade = ?, lab_grade = ? "
						+ " WHERE course_code = ?"
						+ "	   AND amka = ?");
				
				ps.setInt(1, newGrade);
				ps.setInt(2, newLabGrade);
				ps.setString(3, code);
				ps.setInt(4, amka);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		int choice;
		App db = new App();
		
		do { 
			System.out.println("========================================");
			System.out.println("Choose one of the following:");
			System.out.println("1 - Connect to a database.");
			System.out.println("2 - Start/Commit transaction.");
			System.out.println("3 - Cancel transaction.");
			System.out.println("4 - Show labs.");
			System.out.println("5 - Show fields.");
			System.out.println("6 - Rename lab.");
			System.out.println("7 - Rename field.");
			System.out.println("8 - Insert grades for specific course.");
			System.out.println("9 - Exit.");
			Scanner input = new Scanner(System.in);
			System.out.println();
			System.out.println("Enter \"1\", \"2\" , \"3\" , \"4\" , \"5\", \"6\", \"7\", \"8\", \"9\"");
			choice = input.nextInt();
			
			switch( choice ) {
			case 1:
				db.establishCon();
			break;
			case 2:
				db.commitTransaction();
			break;
			case 3:
				db.abortTransaction();
			break;
			case 4:
				db.showLabs();
			break;
			case 5:
				db.showFields();
			break;
			case 6:
				db.renameLab();
			break;
			case 7:
				db.renameField();
			break;
			case 8:
				db.insertGrades();
			break;
			case 9:
				System.out.println("Exiting...");
			break;
			default:
				
			}
		} while( choice != 9);
	}
}

