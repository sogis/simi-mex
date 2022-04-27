package ch.so.agi.mex.db;

import ch.so.agi.mex.ext.RegionFeature;
import ch.so.agi.mex.ext.RegionsReader;
import ch.so.agi.mex.ext.ThemePublication;
import ch.so.agi.mex.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionsReaderImpl implements RegionsReader {

    private static final String QUERY = Util.loadUtf8TextFileContent("region.sql");

    private Connection con;

    public RegionsReaderImpl(Connection con){
        this.con = con;
    }

    @Override
    public String readRegionsForThemePub(ThemePublication themePub) {
        String res = null;

        try(PreparedStatement s = con.prepareStatement(QUERY)){
            s.setString(1, String.valueOf(themePub.getFoo()));
            ResultSet rs = s.executeQuery();

            if(rs.next()){
                res = rs.getString("obj");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return res;
    }
}
