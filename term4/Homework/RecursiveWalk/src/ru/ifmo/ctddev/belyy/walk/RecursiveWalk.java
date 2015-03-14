package ru.ifmo.ctddev.belyy.walk;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiFunction;

/**
 * Created by anton on 12/02/15.
 */
public class RecursiveWalk {
    private static int CHUNK_SIZE = 4096;
    private static int FNV0 = 0x811c9dc5;
    private static int FNV_32_PRIME = 0x01000193;

    private Path root;

    public RecursiveWalk(Path root) {
        this.root = root;
    }

    public void processFiles(BiFunction<Path, Integer, IOException> callback) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                IOException ee = callback.apply(path, calculateChecksum(path));
                if (ee != null) {
                    throw ee;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                IOException ee = callback.apply(path, 0);
                if (ee != null) {
                    throw ee;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static int calculateChecksum(Path path) {
        try (FileInputStream is = new FileInputStream(path.toFile())) {
            byte[] chunk = new byte[CHUNK_SIZE];
            int chunkLen;

            int hval = FNV0;
            while ((chunkLen = is.read(chunk)) != -1) {
                for (int i = 0; i < chunkLen; i++) {
                    hval = (hval * FNV_32_PRIME) ^ (chunk[i] & 0xff);
                }
            }

            return hval;
        } catch (IOException e) {
            return 0;
        }
    }
}
