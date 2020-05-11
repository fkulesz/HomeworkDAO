package com.example.demo.homeworkjdbc.transactionutils;

import com.example.demo.homeworkjdbc.transactionutils.Transaction;

import java.sql.*;

class TransactionDao {
    private static final String URL = "jdbc:mysql://localhost:3306/javastart?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private Connection connection;

    public TransactionDao() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("nie znalazione sterownika do bazy danych");
        } catch (SQLException exception) {
            System.err.println("nie mozna nawiązać połączenia z bazą danych");
        }
    }

    public void displayTransactions(String type) {
            String selectTransactionsSql = "SELECT * FROM transaction WHERE type = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(selectTransactionsSql);
                statement.setString(1, type);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(resultSet.getInt("id"));
                    transaction.setType(resultSet.getString("type"));
                    transaction.setDescription(resultSet.getString("description"));
                    transaction.setAmount(resultSet.getFloat("amount"));
                    transaction.setDate(resultSet.getDate("date"));
                    System.out.println(transaction.toString());
                }
            } catch ( SQLException exception) {
                exception.printStackTrace();
            }
        }

    public void addNewTransaction(Transaction transaction) {
        String addNewTransactionSql = "INSERT INTO transaction(type, description, amount, date) VALUES (?, ? , ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addNewTransactionSql);
            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            java.sql.Date sqlDate = new java.sql.Date(transaction.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.executeUpdate();
            System.out.println("Transakcja dodana do listy");
        } catch (SQLException exception) {
            System.err.println(" nie udalo sie dodać nowej transakcji");
            exception.printStackTrace();
        }
    }

    public void modifyTransactionByID(int id, Transaction transaction) {
        String updateTransactionSql = "UPDATE transaction SET type = ?, description = ?, amount = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateTransactionSql);
            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Transakcja numer " + id + " została zmodyfikowana");
        } catch (SQLException exception) {
            System.err.println(" nie udalo sie zmodyfikować transakcji " + id);
            exception.printStackTrace();
        }
    }

    public void deleteTransaction(int id) {
        String deleteTransactionSql = "DELETE FROM transaction WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteTransactionSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Transakacja " + id + " została usunieta");
        } catch (SQLException exception) {
            System.err.println(" nie udalo sie usunąć transakacji z listy");
            exception.printStackTrace();
        }
    }

    public void close() {
        try{
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
