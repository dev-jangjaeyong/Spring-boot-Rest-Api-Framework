package com.atonm.core.interceptor.Sql;

import com.atonm.kblease.api.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;

/**
 * SQL Filter class
 *
 * @author jangjaeyoung
 *
 */
public class SQLFilter {
    private static final Logger log_sqlFilter = LoggerFactory.getLogger(SQLFilter.class);

    public static final String stringsToCheck[] = { "drop",
            "exec", "exists", "update", "delete", "insert", "cast",
            "sql", "oracle", "()", "information_schema",
            "sleep", "join", "declare", "having", "signed", "alter",
            "union", "where", "shutdown", "grant", "privileges" };

    public static final String jsonStringToCheck[] = {
            "drop", "delete", "insert", "cast",
            "exec", "exists", "sql", "oracle", "information_schema",
            "sleep", "join", "declare", "having", "signed", "alter",
            "union", "where", "shutdown", "grant", "privileges"
    };

    // /* and */
    public static RegexObj regex1 = new RegexObj("(/\\*).*(\\*/)",
            "Found /* and */");

    // -- at the end
    public static RegexObj regex2 = new RegexObj("(--.*)$", "-- at end of sql");

    // ; and at least one " or '
    public static RegexObj regex3 = new RegexObj(";+\"+\'",
            "One or more ; and at least one \" or \'");

    // two or more "
    public static RegexObj regex4 = new RegexObj("\"{2,}+", "Two or more \"");

    // two or more '
    public static RegexObj regex5 = new RegexObj("\'{2,}+", "Two or more \'");

    // anydigit=anydigit
    public static RegexObj regex6 = new RegexObj("\\d=\\d", "anydigit=anydigit");

    // two or more white spaces in a row
    public static RegexObj regex7 = new RegexObj("(\\s\\s)+",
            "two or more white spaces in a row");

    // # at the end
    public static RegexObj regex8 = new RegexObj("(#.*)$", "# at end of sql");

    // two or more %
    public static RegexObj regex9 = new RegexObj("%{2,}+",
            "Two or more \\% signs");

    // admin and one of [; ' " =] before or after admin
    public static RegexObj regex10 = new RegexObj(
            "([;\'\"\\=]+.*(admin.*))|((admin.*).*[;\'\"\\=]+)",
            "admin (and variations like administrator) and one of [; ' \" =] before or after admin");

    // ASCII in hex
    public static RegexObj regex11 = new RegexObj("%+[0-7]+[0-9|A-F]+",
            "ASCII Hex");

    // declare array to hold each regex, can add to this easily
    public static final RegexObj regexes[] = { regex1, regex2, regex3, regex4,
            regex5, regex7, regex8, regex9, regex10 };

    /**
     * Main method / starting point - TEST
     *
     * @param args
     */
    public static void main(String[] args) {
        // hardcode the name of the output file e.g. output80.txt for results from Queries80.txt, or output20.txt for results from Queries20.txt
        // this will be created in the project directory unless you specify an absolute path
        File file = new File("output20.txt");

        // if file doesn't exist then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            // otherwise delete it for fresh output
            file.delete();
        }

        // vars for stats classification
        int condPos = 0;
        int condNeg = 0;
        int truePos = 0;
        int trueNeg = 0;
        int falsePos = 0;
        int falseNeg = 0;

        // string counter
        int stringCounter = 0;

        log_sqlFilter.debug("--Welcome to the CSIT SQL Injection Filter--");
        try {
            File sqlFile = new File("Queries20.txt");
            FileReader fileReader = new FileReader(sqlFile);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line;
            line = "";
            int prediction;
            line = buffReader.readLine();

            while (line != null) {
                stringCounter++;

                log_sqlFilter.debug("-----------------------------");
                log_sqlFilter.debug("\nSample %d = %s\n", stringCounter, line);

                char label = line.charAt(0);

                if (label == '0') {
                    condNeg++;
                } else if (label == '1') {
                    condPos++;
                } else {
                    log_sqlFilter.debug("Invalid label...");
                }
                log_sqlFilter.info("True Label = %c\n", label);

                String sqlString = line.substring(2);
                System.out.printf("SQL = %s\n", sqlString.toLowerCase());

                if (sqlHandler(sqlString)) {
                    prediction = 1;
                    if (Character.getNumericValue(label) == prediction) {
                        truePos++;
                    } else {
                        falsePos++;
                    }
                } else {
                    prediction = 0;
                    if (Character.getNumericValue(label) == prediction) {
                        // correctly rejected
                        trueNeg++;
                    } else {
                        // missed it
                        falseNeg++;
                    }
                }

                outputToFile(sqlString, label, prediction, file);
                line = buffReader.readLine();
            }

            // close resources
            buffReader.close();
            fileReader.close();

            // print results
            log_sqlFilter.info("*******************************");
            log_sqlFilter.info(
                    "Results for dataset file %s.\nOutput file is %s\n",
                    sqlFile.getAbsolutePath(), file.getAbsolutePath());
            log_sqlFilter.info("\nTotal strings read = %s\n", stringCounter);
            log_sqlFilter
                    .info("True malware = %d : Hits (true positives) = %d, Misses (false negatives) = %d\n",
                            condPos, truePos, falseNeg);
            log_sqlFilter
                    .info("True benign = %d : Correct Rejections (true negatives) = %d, False Alarms (false positives) = %d\n",
                            condNeg, trueNeg, falsePos);

            log_sqlFilter
                    .info("Detection Rate (True Positive Rate - how well the filter correctly classifies malware) = %.1f%%\n",
                            (double) truePos / (double) condPos * 100);
            log_sqlFilter
                    .info("Rejection Rate (True Negative Rate - how well the filter correctly classifies benign) = %.1f%%\n",
                            (double) trueNeg / (double) condNeg * 100);
            log_sqlFilter.info("Accuracy = %.1f%%\n",
                    (double) (truePos + trueNeg) / (double) (condPos + condNeg)
                            * 100);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Method to kick off filtering a SQL string
     * @param sqlString - the SQL sample
     * @return a bool, if it true then it is malicious, if false it is benign
     */
    public static boolean sqlHandler(String sqlString) {
        boolean pass1 = false;
        boolean pass2 = false;

        pass1 = sqlStringChecker(sqlString);
        pass2 = sqlRegexChecker(sqlString);

        if (pass1 || pass2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to check for stringsToCheck in the string passed in
     * @param sqlToCheck - the SQL sample
     * @return a bool, if true then it is malicious, if false then benign
     */
    public static boolean sqlStringChecker(String sqlToCheck) {
        boolean pass = false;
        log_sqlFilter.debug("\nRunning SQL String Checker");
        if(StringUtil.isEmpty(sqlToCheck)) return pass;
        sqlToCheck = sqlToCheck.toLowerCase();

        for (String command : stringsToCheck) {
            if (sqlToCheck.contains(command)) {
                log_sqlFilter
                        .info("SQL string found (%s), predicted label = 1\n",
                                command);
                if (!pass) {
                    pass = true;
                }
            }
        }

        if (!pass) {
            log_sqlFilter.debug("No SQL command found, predicted label = 0");
        }

        return pass;
    }

    /**
     * Method to check SQL sample against the regexes
     * @param sqlToCheck
     * @return a bool, true if malicious, false if benign
     */
    public static boolean sqlRegexChecker(String sqlToCheck) {
        log_sqlFilter.debug("\nRunning SQL Regex Checker");
        boolean pass = false;
        boolean overall = false;
        Matcher matcher;
        if(StringUtil.isEmpty(sqlToCheck)) return pass;
        sqlToCheck = sqlToCheck.toLowerCase();

        // regex checking
        for (RegexObj regex : regexes) {
            matcher = regex.getRegexPattern().matcher(sqlToCheck);
            pass = matcher.find();

            if (pass) {
                log_sqlFilter
                        .info("Malicious input found via regex (%s), predicted label = 1\n",
                                regex.getDescription());
            } else {
                log_sqlFilter
                        .info("No malicious input found via regex (%s), predicted label = 0\n",
                                regex.getDescription());
            }

            if ((pass) && (!overall)) {
                overall = true;
            }
        }
        return overall;
    }


    public static boolean sqlJsonStringChecker(String sqlToCheck) {
        boolean pass = false;
        log_sqlFilter.debug("\nRunning SQL String Checker");
        if(StringUtil.isEmpty(sqlToCheck)) return pass;
        sqlToCheck = sqlToCheck.toLowerCase();

        for (String command : jsonStringToCheck) {
            if (sqlToCheck.contains(command)) {
                log_sqlFilter
                        .info("SQL string found (%s), predicted label = 1\n",
                                command);
                if (!pass) {
                    pass = true;
                }
            }
        }

        if (!pass) {
            log_sqlFilter.info("No SQL command found, predicted label = 0");
        }

        return pass;
    }


    /**
     * Method for outputing basic classification info to file
     * @param SQL - the SQL sample
     * @param label - the true label
     * @param prediction - the predicted label
     * @param file - the file to write to
     */
    public static void outputToFile(String SQL, char label, int prediction, File file) {
        // create result string
        String result = SQL + " True Label = " + label + " Predicted Label = "
                + prediction + "\r\n";

        try {
            // true = append to end of file, false = write from the start
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);

            // do the writing
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(result);

            // close resources
            bufferWriter.close();
            fileWriter.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
