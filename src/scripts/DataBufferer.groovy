package scripts

import static scripts.support.JsonMethods.*
import static scripts.support.PathConstants.*

/**
 * Combines the raw accelerometer and gyroscope data files to one single file with the same time frequencies.
 *
 * Arguments: Two digit string representing the trial iteration number.
 *
 * @author Lawrence Thatcher
 */
class DataBufferer
{
    static def output = [[]]

    //--Main--

    /**
     * @param args Two digit string representing the trial number
     */
    public static void main(String[] args)
    {
        String trial_num = args[0]
        String output_num = trial_num

        String label = args[1]

        if (args.length > 2)
        {
            output_num = args[2]
        }

        println "reading directory locations"
        def input_gyro = new File(INPUT_PATH_PREFIX + trial_num + GYRO_FILE_POSTFIX)
        def input_accel = new File(INPUT_PATH_PREFIX + trial_num + ACCEL_FILE_POSTFIX)

        def outputDir = new File(OUTPUT_PATH_PREFIX + output_num + OUTPUT_FILE_POSTFIX)

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
        println "updating json file"
        addElementToJSON(output_num,label)
    }

    //--I/O Methods--
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
}
