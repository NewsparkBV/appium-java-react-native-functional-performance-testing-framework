package newspark.utils.performancetestutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("cucumber-glue")
public class DumpsysGFX {

    @Value("${app.package:com.newspark}")
    private String appPackage;
    private static Logger logger = Logger.getLogger(DumpsysGFX.class.getName());
    private static String os = System.getProperty("spring.profiles.active");
    private static String buildNr = System.getProperty("buildNr");

    public static String pBuilder(ArrayList<String> commandParams, Boolean redirectOutput, Integer secTimeout) {
        String pBuilder = "";
        try {
            ProcessBuilder b = new ProcessBuilder("/bin/bash/");
            b.command(commandParams);
            b.redirectErrorStream();
            Process p = b.start();
            p.waitFor(secTimeout, TimeUnit.SECONDS);
            Thread.sleep(5000);
            if(redirectOutput == true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
                }
                pBuilder = builder.toString();
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(Level.SEVERE, "Process builder error: ", exception);
        }
        return pBuilder;
    }

    public static void resetDumpsys(String appPackage) {
        if(os.equalsIgnoreCase("ios")) {
            logger.log(Level.SEVERE,"iOS device, dunno what to do !");
        }
        else {
            try {
                ArrayList<String> commandParams = new ArrayList<>();
                commandParams.add("adb");
                commandParams.add("shell");
                commandParams.add("dumpsys");
                commandParams.add("gfxinfo");
                commandParams.add(appPackage);
                commandParams.add("reset");
                pBuilder(commandParams,false,60);
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Unable to reset dumpsys", exception);
            }
        }
    }

    public static void writeDumpsysGFX(String scenarioName, String appPackage) {
        if( os.equalsIgnoreCase("ios")) {
            logger.log(Level.SEVERE,"iOS device, dunno what to do !");
        }
        else {
            FileWriter fileWriter = null;
            BufferedReader bufferedReader = null;

            /** Dumpsys logging */

            String dumpFile = "target" + File.separator + "performance" + File.separator + scenarioName
                    + File.separator + LocalDateTime.now() + "gfxinfo.dumpsys.log";

            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("adb", "shell", "dumpsys", "gfxinfo", appPackage, "framestats");
                processBuilder.redirectErrorStream();
                Process process = processBuilder.start();
                Files.createDirectories(Paths.get("target" + File.separator + "performance" + File.separator + scenarioName));

                File file = new File(dumpFile);
                fileWriter = new FileWriter(file);
                bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.append(line);
                    fileWriter.append(System.lineSeparator());
                }
                process.waitFor();
                if (process.exitValue() != 0) {
                    throw new Exception("Error while taking dumpsys, exitCode=" +
                            process.exitValue());
                }

            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Unable to take a dumpsys", exception);
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            csvWriter(dumpFile, scenarioName);
        }
    }

    public static void csvWriter(String fileToRead, String scenarioName){
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;

        try {
            /**Extract data you need and save to CSV file*/
            List<String> logContent = Files.readAllLines(Paths.get(fileToRead));

            StringBuilder sb = new StringBuilder();
            for (String s : logContent)
            {
                sb.append(s);
                sb.append("\t");
            }
            String source = sb.toString();

            String Frames  = "Total\\s+?frames\\s+?rendered:\\s+?(\\d+?)\\s+?";
            String JankyFrames = "Janky\\s+?frames:\\s+?(\\d+?)\\s+?";
            String P90 = "90th percentile: ([\\d]+?)ms";
            String P95 = "95th percentile: ([\\d]+?)ms";
            String P99 = "99th percentile: ([\\d]+?)ms";
            String mem = "Total.memory.usage:[\\s]+?[\\d]+?.bytes,(\\s\\d\\d\\.\\d\\d)\\sMB";

            List patternList = new ArrayList();
            patternList.add(Frames);
            patternList.add(JankyFrames);
            patternList.add(P90);
            patternList.add(P95);
            patternList.add(P99);
            patternList.add(mem);

            String headerStr = "Frames,JankyFrames,90P,95P,99P,Memory";
            String contentStr = "";
            Pattern pattern;
            Matcher matcher;

            for(int i=0; i<patternList.size(); i++) {
                pattern = Pattern.compile(String.valueOf(patternList.get(i)));
                matcher = pattern.matcher(source);
                if (matcher.find()) {
                    contentStr = contentStr + String.valueOf(matcher.group(1)) + ",";
                }
            }

            String csvToWrite = "target" + File.separator + "performance" + File.separator + scenarioName
                    + File.separator +  "performance_summary_" + buildNr +".csv";
            File csv = new File(csvToWrite);
            BufferedWriter out = new BufferedWriter(new FileWriter(csv, true));
            BufferedReader in = new BufferedReader(new FileReader(csv));

           /** Only add headers if file is empty */
            if (in.readLine() == null){
                out.write(headerStr);
                out.newLine();
            }
                       //remove last "," to make a correct csv
            out.write(contentStr.substring(0, contentStr.length() - 1));
            out.newLine();
            in.close();
            out.close();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Unable to write CSV", exception);
        } finally {
            if (fileWriter != null) {
                try { fileWriter.close(); } catch (Exception e) { e.printStackTrace(); }
            }
            if (bufferedReader != null) {
                try { bufferedReader.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

}
