package ch.so.agi.mex;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MexTest {

    private static Connection con;

    @Test
    void exportXml() throws IOException {
        Mex.exportXml(Files.createTempFile(null, null), con);
    }

    @BeforeAll
    static void beforeAll() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:postgresql:postgres",
                    "postgres",
                    "postgres"
            );
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AfterAll
    static void afterAll() {
        if(con == null)
            return;

        try {
            con.close();
        } catch (SQLException ignored) {}
    }
}