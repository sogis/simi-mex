package ch.so.agi.mex;

import ch.so.agi.mex.db.RegionsReaderImpl;
import ch.so.agi.mex.db.ThemePubIterator;
import ch.so.agi.mex.ext.Bean2Markup;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mex {

    private static final String C_CONNECTION = "c";
    private static final String U_USER = "u";
    private static final String P_PASSWORD = "p";
    private static final String X_XML_FILE_PATH = "x";

    private static Logger log = LoggerFactory.getLogger(Mex.class);

    public static void main(String[] args) throws SQLException, ParseException {
        Options opt = initOptions();

        if(args == null || args.length == 0){
            HelpFormatter f = new HelpFormatter();
            f.printHelp(
                    "java -jar mex.jar [options]",
                    "[options]:",
                    opt,
                    "Version: " + Mex.class.getPackage().getImplementationVersion());
            return;
        }

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(opt, args);

        Path xmlOutput = Path.of(cmd.getOptionValue(X_XML_FILE_PATH));
        try(Connection con = createCon(cmd)){
            exportXml(xmlOutput, con);
        }
    }

    public static File exportXml(Path xmlOutput, Connection con){

        try(ThemePubIterator iter = new ThemePubIterator(con)) {
            Bean2Markup.runBeans2Xml(xmlOutput, iter, new RegionsReaderImpl(con));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private static Connection createCon(CommandLine cmd) throws SQLException {

        String conUrl = cmd.getOptionValue(C_CONNECTION);
        String user = cmd.getOptionValue(U_USER);
        String pass = cmd.getOptionValue(P_PASSWORD);

        log.info("Connecting to url {} with user {} and pass {}", conUrl, user, pass.replaceAll(".*", "*"));

        return DriverManager.getConnection(conUrl, user, pass);
    }

    private static Options initOptions(){
        Options os = new Options();
        os.addOption(C_CONNECTION, "connection", true, "Connection string as jdbc url (with schema)");
        os.addOption(U_USER, "user", true, "database user for connect");
        os.addOption(P_PASSWORD, "pass", true, "database password for connect");
        os.addOption(X_XML_FILE_PATH, "xmlfile", true, "File path destination for the resulting xml file");

        return os;
    }
}
