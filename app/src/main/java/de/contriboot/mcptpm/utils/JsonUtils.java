package de.contriboot.mcptpm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JsonUtils {
    public static <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static String createZippedBase64(Object data, String filename) throws IOException {
        // Convert object to JSON string (assuming you're using Jackson)
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(data);

        // Create byte array output stream to hold zip data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create zip output stream with maximum compression
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            // Set compression level to maximum (equivalent to level 9)
            zipOutputStream.setLevel(Deflater.BEST_COMPRESSION);
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);

            // Create zip entry for the JSON file
            ZipEntry entry = new ZipEntry(filename);
            zipOutputStream.putNextEntry(entry);

            // Write JSON content to zip
            zipOutputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        }

        // Convert to base64
        byte[] zipBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(zipBytes);
    }
}
