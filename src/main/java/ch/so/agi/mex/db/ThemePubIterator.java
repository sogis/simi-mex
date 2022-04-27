package ch.so.agi.mex.db;

import ch.so.agi.mex.ext.ThemePublication;
import ch.so.agi.mex.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Iterator;

public class ThemePubIterator implements Iterator<ThemePublication>, AutoCloseable {

    private static Logger log = LoggerFactory.getLogger(ThemePubIterator.class);

    private static final String QUERY = Util.loadUtf8TextFileContent("theme_pub.sql");

    private ResultSet resultSet;
    private ObjectMapper mapper;
    private int fetchCount = 0;

    public ThemePubIterator(Connection con){
        this.resultSet = createResultSet(con);
        this.mapper = new ObjectMapper();
    }

    private static ResultSet createResultSet(Connection con) {

        ResultSet res = null;

        try{
            PreparedStatement s = con.prepareStatement(QUERY);
            s.setFetchSize(10);
            res = s.executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return res;
    }

    @Override
    public boolean hasNext() {
        boolean res = false;

        try{
            res = !resultSet.isLast();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return res;
    }

    @Override
    public ThemePublication next() {
        ThemePublication res = null;

        try{
            boolean hadNext = resultSet.next();
            if(!hadNext)
                throw new RuntimeException("Code error: Resultset has no more rows to return.");

            res = mapper.readValue(
                    resultSet.getString("obj"),
                    ThemePublication.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        fetchCount++;

        return res;
    }

    @Override
    public void close() throws IOException {

        log.info("Closing resultset after fetching {} themepub rows", fetchCount);

        if(resultSet == null)
            return;

        try{
            if(!resultSet.isClosed())
                resultSet.close();
        }
        catch(SQLException ignored){}
    }
}
