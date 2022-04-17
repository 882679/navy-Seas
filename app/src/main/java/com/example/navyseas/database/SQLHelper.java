package com.example.navyseas.database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {

    private static String dbName = "./database.db";

    private Connection connection = null;

    public SQLHelper() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);// create a database connection (side effect create DB)

            ResultSet ris = executeQuery("SELECT * FROM Groups"); //verifica che nel database l'utente sia già registrato
            if (ris.next()) { //il primo valore è antecedente a valori del resultset, perciò se questo valore è l'ultimo il risultato è vuoto
                System.out.println("!!!! "+ ris);
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());// if the error message is "out of memory", it probably means no database file is found
        }
    }

    public ResultSet executeQuery(String s) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(s);
        return statement.executeQuery();
    }

    public void executeUpdate(String s) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(s);
        statement.executeUpdate();
    }

}
