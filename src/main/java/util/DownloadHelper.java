package util;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DownloadHelper {

    private static final String ROOT_FOLDER = System.getProperty("user.dir") + File.separator + "Swiggy";
    private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static final String TEMP_DOWNLOAD_PATH = ROOT_FOLDER + File.separator + "tempDownloads";

    // Get outlet folder path
    public static String getOutletFolder(String outlet,String date) {
        return ROOT_FOLDER + File.separator + date + File.separator + "Outlet_" + outlet;
    }

    // Move downloaded files to outlet folder
    public static void moveDownloadedFiles(String outlet,String date) throws IOException {
        String outletPath = getOutletFolder(outlet,date);
        new File(outletPath).mkdirs();

        File tempFolder = new File(TEMP_DOWNLOAD_PATH);
        for (File file : Objects.requireNonNull(tempFolder.listFiles())) {
            Path source = file.toPath();
            Path target = Paths.get(outletPath, file.getName());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Moved reports to " + outletPath);
    }

    public static void waitForDownloads(String folder, int expectedCount, int timeoutSec) throws InterruptedException {
        File dir = new File(folder);
        int waited = 0;
        while (waited < timeoutSec) {
            String[] files = dir.list((d, name) -> name.endsWith(".pdf") || name.endsWith(".xlsx"));
            if (files != null && files.length >= expectedCount) return;
            Thread.sleep(1000);
            waited++;
        }
        throw new RuntimeException("Timeout: files not downloaded within " + timeoutSec + " seconds");
    }

}

