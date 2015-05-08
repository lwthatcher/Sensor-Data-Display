package scripts.support

/**
 * Created by lthatch1 on 5/8/2015.
 * @author Lawrence Thatcher
 */
class PathConstants
{
    //--Path Formatter Functions--
    public static String getTsvPath(String id)
    {
        return OUTPUT_PATH_PREFIX + id + OUTPUT_FILE_POSTFIX
    }

    //--Path Constants--
    public static final String INPUT_PATH_PREFIX = directoryRoot + "/data/input/trial_"
    public static final String GYRO_FILE_POSTFIX = "/gyroscope.txt"
    public static final String ACCEL_FILE_POSTFIX = "/accelerometer.txt"
    public static final String OUTPUT_PATH_PREFIX = directoryRoot + "/web/tsv/trial_"
    public static final String OUTPUT_FILE_POSTFIX = ".tsv"
    public static final String JSON_DIR = directoryRoot + "/web/tsv/index.json"
    public static final String ARFF_OUTPUT_DIR = directoryRoot + "/data/arff/"

    //--Helper Methods--
    public static String getDirectoryRoot()
    {
        String path = new File("").getAbsolutePath();
        return formatPath(path);
    }

    public static String formatPath(String path)
    {
        return path.replace("\\", "/");
    }
}
