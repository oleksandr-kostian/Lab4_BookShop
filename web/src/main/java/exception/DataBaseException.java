package exception;

import org.apache.log4j.Logger;
import java.util.Arrays;

/**
 * Class that describes the exception for database.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class DataBaseException  extends Exception {
    private static final Logger LOG = Logger.getLogger(DataBaseException.class);

    private String message;

    public DataBaseException(String message, Exception e){
        super(message,e);
        this.message = message;
        LOG.error(Arrays.toString(e.getStackTrace()).replaceAll(" ", "\t\n"));
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "is: " + message;
    }
}
