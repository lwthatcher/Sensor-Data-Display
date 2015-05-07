package scripts
/**
 *
 * @author Lawrence Thatcher
 */
class DataBufferer
{
    static File input_gyro
    static File input_accel
    static def output = [[]]

    public static void main(String[] args)
    {
        println "reading directory locations"
        input_gyro = new File(directoryRoot + args[0])
        input_accel = new File(directoryRoot + args[1])

        def outputDir = new File(directoryRoot + args[2])

        println "reading in input files"
        def gyros_input =  new FileInputStream(input_gyro)
        def gyros = gyros_input.readLines()
        gyros_input.close()

        def accels_input = new FileInputStream(input_accel)
        def accels_data = accels_input.readLines()
        accels_input.close()
        def accels = accels_data.iterator()

        println "generating new format"
        def accel = accels.next().split("\t")
        int at = Integer.parseInt(accel[0])

        for (String line : gyros)
        {
            def gyro = line.split()
            int gt = Integer.parseInt(gyro[0])

            while (accels.hasNext() && at <= gt)
            {
                createOutput(gyro,accel)
                accel = accels.next().split("\t")
                at = Integer.parseInt(accel[0])
            }
        }
        println "writing output"
        writeOutput(outputDir)
        println "file creation complete"
    }

    private static void writeOutput(File outputDir)
    {
        def out = new PrintWriter(outputDir)
        writeHeader(out)
        for (def entry : output)
        {
            if (entry.size() != 0)
            {
                String line = ""
                for (int i = 0; i < 6; i++)
                {
                    line += entry[i]
                    line += "\t"
                }
                line += entry[6]
                out.println(line)
            }
            out.flush()
        }
        out.close()
    }

    private static void writeHeader(PrintWriter out)
    {
        out.println("time\taccelerometer_x\taccelerometer_y\taccelerometer_z\tgyroscope_x\tgyroscope_y\tgyroscope_z\t")
    }

    private static void createOutput(String[] gyro, String[] accel)
    {
        def entry = []
        //time
        entry.add(accel[0])
        //accelerometer x
        entry.add(accel[1])
        //accelerometer y
        entry.add(accel[2])
        //accelerometer z
        entry.add(accel[3])
        //gyroscope x
        entry.add(gyro[1])
        //gyroscope y
        entry.add(gyro[2])
        //gyroscope z
        entry.add(gyro[3])
        output.add(entry)
    }

    public static String getDirectoryRoot()
    {
        String path = new File("").getAbsolutePath();
        return formatPath(path);
    }

    protected static String formatPath(String path)
    {
        return path.replace("\\", "/");
    }
}
