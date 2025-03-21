package com.rbc.util;

import com.opencsv.CSVReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.apache.pdfbox.pdfparser.PDFParser.load;

public class FileUtil {

    /*
    Get user directory path, e.g, Downloads, Document
    @Parameter
    directoryName = e.g, Downloads
     */
    public static String getDirectoryPath(String directoryName) {
        String home = System.getProperty("user.home");
        return home + File.separator + directoryName;
    }

    /*
    Get the latest file in a directory, e.g, Downloads, Document
     @Parameter
    directoryName = e.g, Downloads
     */
    public static String getLatestDownloadedFile(String directoryName) {
        String directoryPath = getDirectoryPath(directoryName);
        File directory = new File(directoryPath);

        // Ensure the directory exists and is actually a directory
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a valid directory.");
        }

        // Get all the files in the directory
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            return null; // No files found in the directory
        }

        // Initialize variable to hold the latest file
        File latestFile = null;

        // Loop through all files and find the one with the latest last modified time
        for (File file : files) {
            if (file.isFile()) { // Ensure it's a file and not a directory
                if (latestFile == null || file.lastModified() > latestFile.lastModified()) {
                    latestFile = file; // Update the latest file
                }
            }
        }
        // Return the name of the latest file, or null if no file is found
        return latestFile != null ? latestFile.getName() : null;
    }


    public static String getPropValue(String propertyValue) {

        String filePath = System.getProperty("user.dir") + "/" + "src/test/java/config.properties";
        Properties prop = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            System.out.println("exception cought: " + e.getMessage());
            System.out.println("Exception occurred please check the file path: " + filePath);
        }
        return prop.getProperty(propertyValue);
    }

    public static boolean deleteFile(String directoryName,String fileName) {
//        String latestFileName = getDownloadFile(directoryName,fileName);
        String directoryPath = getDirectoryPath(directoryName);
        String filePath = directoryPath + File.separator + fileName;
        boolean isDelete = false;
        File file = new File(filePath);
        if(file.exists()) {
            isDelete = file.delete();
            if (isDelete) {
                System.out.println("File Deleted Successfully! - ");
            } else {
                System.out.println("Failed to delete the file");
            }
        } else {
            System.out.println("The specified file does not exist.");
        }
        return isDelete;
    }

    public static void isFileDownload(String filePath) throws InterruptedException {
        Path path = Paths.get(filePath);
        boolean isFileDownloadedResult = true;

        long endTime = System.currentTimeMillis() + (60 * 1000);
        while (!Files.exists(path)) {
            if (System.currentTimeMillis() < endTime) {
                Thread.sleep(1000);
            } else {
                isFileDownloadedResult = false;
                break;
            }
        }
        if (!isFileDownloadedResult) System.out.println("File not downloaded...");
        assert isFileDownloadedResult;
    }

    //Check if the file is downloaded with latest modified within 1 min than the current time
    public static void isFileDownloaded(String filPath) {
        File file = new File(filPath);

        boolean isFileDownloadedResult = true;

        //with in 60 seconds
        long endTime = System.currentTimeMillis() + (60 * 1000);

        long lastModified = file.lastModified();
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - lastModified;

        if (timeDifference <= endTime) {
            isFileDownloadedResult = true; // The file is considered "downloaded" or recently modified
        } else {
            isFileDownloadedResult = false; // The file was modified too long ago
        }

        if (!isFileDownloadedResult) {
            System.out.println("File not downloaded...");
        }
        assert isFileDownloadedResult;
    }

    /*
    @Parameters
    directoryName = E.g, Downloads
    fileName = name of the file
     */
    public static String getDownloadFile(String directoryName, String fileName) {
        String directoryPath = getDirectoryPath(directoryName);
        String filePath = directoryPath + File.separator + fileName;
        isFileDownloaded(filePath);
        String downloadFilePath = getLatestDownloadedFile(directoryName);
        System.out.println("Downloaded file path : " + downloadFilePath);
        return downloadFilePath;
    }

    public static String readPDFData(String filePath) {
        String pdfData = "";
        try {
            File file = new File(filePath);
            PDDocument document = load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfData = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfData;
    }

    public static List<List<String>> readCSVData(String filePath) {
        List<List<String>> csvFileData = new ArrayList();
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                List<String> rowData = new ArrayList<>(Arrays.asList(nextRecord));
                csvFileData.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFileData;
    }

    @DataProvider(name = "loginData")
    public static String[][] getExcelRecords(String excelFilePath, String sheetName) throws IOException {

//        String fileName = "src/test/java/com/rbc/ui/testData/testData.xlsx";
//        String filePath = System.getProperty("user.dir")+ "/" + fileName;

        File file = new File(excelFilePath);
        InputStream fis = new FileInputStream(file);
        Workbook workbook;
        Sheet sheet;

        if (excelFilePath.contains(".xlsx")) {
            workbook = new XSSFWorkbook(fis);
            sheet = (XSSFSheet) workbook.getSheet(sheetName);
        } else {
            workbook = new HSSFWorkbook(fis);
            sheet = (HSSFSheet) workbook.getSheet(sheetName);
        }

        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = sheet.getRow(0).getLastCellNum(); //getting column count based on headers
        String[][] data = new String[rowCount - 1][columnCount];

        for (int i = 0; i < rowCount - 1; i++) {
            for (int j = 0; j < columnCount; j++) {

                Cell cell = sheet.getRow(i + 1).getCell(j);

                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            data[i][j] = cell.getStringCellValue().trim();
                            break;
                        case NUMERIC:
                            data[i][j] = String.valueOf(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            data[i][j] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            data[i][j] = "";
                            break;
                        default:
                            data[i][j] = "";
                            break;
                    }
                } else {
                    data[i][j] = "";
                }
//                System.out.println(data[i][j]);
            }
        }
        workbook.close();
        fis.close();
        return data;
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        String fileName = "src/test/java/com/rbc/ui/testData/testData.xlsx";
//        String filePath = System.getProperty("user.dir") + "/" + fileName;
//
//        for(String[] s : FileUtil.getExcelRecords(filePath,"Sheet1")){
//            for(String s1: s) {
//                System.out.println(s1);
//            }
//            System.out.println();
//        }
//    }

}
