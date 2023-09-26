package org.laba;

import org.testng.annotations.Test;


import javax.sql.rowset.JdbcRowSet;
import java.io.BufferedInputStream;
import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class Main
{
    private static Connection connection;

    public static void main( String[] args )
    {

        System.setProperty("file.encoding", "UTF-8");
        try {
            init();
            startAnalyse();
            close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private static Connection getNewConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void startAnalyse() throws SQLException{

        factsAnalyse();
        rulesAnalyse();
        showLastRule();

    }

    private static void showLastRule() throws SQLException {
        Statement selectStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        selectStatement.execute("SELECT name " +
                "FROM knowledge_base.working_memory ORDER BY step_number DESC LIMIT 1;");
        ResultSet resultSet = selectStatement.getResultSet();
        resultSet.next();
        System.out.println("у вас " + resultSet.getString("name"));
    }

    private static void rulesAnalyse() throws SQLException {
        Statement selectStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        Statement insertStatement = connection.createStatement();

        insertStatement.executeUpdate("update knowledge_base.rules set use_flag = 0");
        selectStatement.execute("select * from knowledge_base.rules");
        ResultSet rules = selectStatement.getResultSet();
        Integer ruleId;
        String ruleName;
        int i = 1;

        while (rules.next()){
            ruleId = rules.getInt("id");
            ruleName = rules.getString("rule_name");

            insertStatement.executeUpdate(
                    "update knowledge_base.rules set use_flag = "
                    + i + " where id = " + ruleId);
            i++;

            if (checkCurrentRule(ruleId)){
                insertStatement.executeUpdate(
                        "insert into knowledge_base.working_memory (name, id_rule) values ('"
                        + ruleName + "', " + ruleId + ")");
            }
        }
    }

    private static boolean checkCurrentRule(Integer ruleId) throws SQLException {
        Statement selectStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        Statement secondSelectStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet factsForCurrentRule;
        int factId;

        selectStatement.execute(
                "select id_fact from knowledge_base.facts_and_rules where id_rule = "
                        + ruleId.toString());

        factsForCurrentRule = selectStatement.getResultSet();
        while (factsForCurrentRule.next()){
            factId = factsForCurrentRule.getInt("id_fact");
            secondSelectStatement.execute(
                    "select * from knowledge_base.working_memory where id_fact = "
                            + factId);
            if (!secondSelectStatement.getResultSet().next()){
                return false;
            }
        }

        return true;
    }

    private static void factsAnalyse() throws SQLException {
        Statement selectStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        Statement insertStatement = connection.createStatement();

        selectStatement.execute("select * from knowledge_base.facts");
        insertStatement.executeUpdate("delete from knowledge_base.working_memory");
        ResultSet resultSet = selectStatement.getResultSet();
        Scanner console = new Scanner(System.in);


        while (resultSet.next()){
            System.out.println("у вас " + resultSet.getString("fact_name") + "?");
            String answer = console.nextLine().trim().toLowerCase(Locale.ROOT);
            if (answer.equals("да") || answer.equals("yes")){
                insertStatement.executeUpdate(
                        "insert into knowledge_base.working_memory (name, id_fact) values ('"
                                + resultSet.getString("fact_name") + "', "
                                + resultSet.getInt("id") + ")");
            }
        }
    }

    public static void init() throws SQLException{
        connection = getNewConnection();
    }

    public static void close() throws SQLException{
        connection.close();
    }
}
