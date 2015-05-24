package ru.ifmo.ctddev.belyy.filecopy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicReference;

public class UIApplication extends JFrame {
    private static final int CHUNK_SIZE = 4096;
    private static final Font initialFont = new Font("Inconsolata LGC", Font.PLAIN, 16);
    private static final Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");

    private AtomicReference<Thread> activeThread;
    private final JProgressBar progress;
    private final JLabel fileName;
    private final JLabel elapsedTime;
    private final JLabel estimatedTime;
    private final JLabel averageSpeed;
    private final JLabel currentSpeed;
    private final int initialWidth, initialHeight;

    private long currentSize;
    private long totalSize;
    private long startTime;
    private long previousUpdateTime;
    private long prevElapsedTime;

    public UIApplication() {
        super("Copying...");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        activeThread = new AtomicReference<>();

        // Create panel for GUI elements
        Container pane = getContentPane();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));

        // Create labels and progress bar
        elapsedTime = new JLabel("Elapsed time:");
        estimatedTime = new JLabel("Estimated time:");
        averageSpeed = new JLabel("Average speed:");
        currentSpeed = new JLabel("Current speed:");

        elapsedTime.setFont(initialFont);
        estimatedTime.setFont(initialFont);
        averageSpeed.setFont(initialFont);
        currentSpeed.setFont(initialFont);

        infoPanel.add(elapsedTime);
        infoPanel.add(estimatedTime);
        infoPanel.add(averageSpeed);
        infoPanel.add(currentSpeed);

        pane.add(infoPanel, BorderLayout.PAGE_START);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));

        fileName = new JLabel();
        fileName.setFont(initialFont);
        bottomPanel.add(fileName);

        progress = new JProgressBar(0, 10000);
        progress.setValue(0);
        progress.setStringPainted(true);
        bottomPanel.add(progress);

        pane.add(bottomPanel, BorderLayout.PAGE_END);

        initialWidth = getWidth();
        initialHeight = getHeight();

        // Add resize listener
        pane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                float scale = Math.min(
                        (float) e.getComponent().getWidth() / initialWidth,
                        (float) e.getComponent().getHeight() / initialHeight);
                Font infoFont = new Font(initialFont.getName(), initialFont.getStyle(), (int) (scale * initialFont.getSize()));
                Font fileFont = new Font(initialFont.getName(), initialFont.getStyle(), (int) (scale * 0.75f * initialFont.getSize()));
                elapsedTime.setFont(infoFont);
                estimatedTime.setFont(infoFont);
                averageSpeed.setFont(infoFont);
                currentSpeed.setFont(infoFont);
                fileName.setFont(fileFont);
            }
        });

        // Calculate frame size and display it
        pack();
        setVisible(true);
    }

    public synchronized void updateProgress() {
        long updateTime = System.nanoTime();
        long elapsedTimeVal = (updateTime - startTime) / 1000000000;
        if (prevElapsedTime != elapsedTimeVal) {
            System.out.println(prevElapsedTime + " " + elapsedTimeVal);
            long deltaTime = updateTime - previousUpdateTime;
            if (deltaTime != 0 && elapsedTimeVal != 0) {
                long currentSpeedVal = (long) 1000000000 * CHUNK_SIZE / deltaTime / 1024; // in Kb / s
                long averageSpeedVal = currentSize / elapsedTimeVal / 1024; // in Kb / s
                long estimatedTimeVal = (totalSize - currentSize) / 1024;

                elapsedTime.setText("Elapsed time: " + elapsedTimeVal + " s");
                if (averageSpeedVal != 0) {
                    estimatedTimeVal /= averageSpeedVal;
                    estimatedTime.setText("Estimated time: " + estimatedTimeVal + " s");
                } else {
                    estimatedTime.setText("Estimated time: NA");
                }
                currentSpeed.setText("Current speed: " + currentSpeedVal + " Kb/s");
                averageSpeed.setText("Average speed: " + averageSpeedVal + " Kb/s");

                prevElapsedTime = elapsedTimeVal;
            }
        }

        progress.setValue((int) (10000 * currentSize / totalSize));
        previousUpdateTime = updateTime;
    }

    private void notifyError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Copy error", JOptionPane.ERROR_MESSAGE, errorIcon);
    }

    private boolean createDirectories(Path dir) {
        if (Files.exists(dir)) {
            if (Files.isDirectory(dir)) {
                return true;
            } else {
                notifyError("" + dir + " already exists and is not a directory");
                return false;
            }
        } else {
            try {
                Files.createDirectories(dir);
                return true;
            } catch (IOException e) {
                notifyError("Unable to create " + dir);
                return false;
            }
        }
    }

    private boolean copyFile(Path inputPath, Path outputPath) {
        try (FileInputStream is = new FileInputStream(inputPath.toFile());
             FileOutputStream os = new FileOutputStream(outputPath.toFile())) {
            fileName.setText(inputPath.toString());
            byte[] chunk = new byte[CHUNK_SIZE];
            int chunkLen;

            while ((chunkLen = is.read(chunk)) != -1) {
                os.write(chunk, 0, chunkLen);
                currentSize += chunkLen;
                updateProgress();
            }

            return true;
        } catch (IOException e) {
            notifyError("Unable to copy " + inputPath);
            return false;
        }
    }

    public void copyFiles(final Path from, final Path to, Runnable callback) {
        Thread newThread = new Thread(() -> {
            try {
                if (Files.isDirectory(from)) {
                    // Create destination directories
                    if (!createDirectories(to)) {
                        return;
                    }
                }

                startTime = previousUpdateTime = System.nanoTime();
                prevElapsedTime = -1;

                // Calculate `from` directory tree size
                currentSize = 0;
                totalSize = 0;
                Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path inputPath, BasicFileAttributes attrs) {
                        if (checkIsInterrupted()) {
                            return FileVisitResult.TERMINATE;
                        }

                        totalSize += attrs.size();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path path, IOException e) {
                        notifyError("Error while counting size of " + path);
                        return FileVisitResult.TERMINATE;
                    }
                });

                // Copy files
                Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        if (checkIsInterrupted()) {
                            return FileVisitResult.TERMINATE;
                        }

                        Path relativeDir = from.relativize(dir);
                        Path outputDir = to.resolve(relativeDir);
                        return createDirectories(outputDir) ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path inputPath, BasicFileAttributes attrs) {
                        if (checkIsInterrupted()) {
                            return FileVisitResult.TERMINATE;
                        }

                        Path relativePath = from.relativize(inputPath);
                        Path outputPath = to.resolve(relativePath);
                        return copyFile(inputPath, outputPath) ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path path, IOException e) {
                        notifyError("Error while reading " + path);
                        return FileVisitResult.TERMINATE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Exit thread
                activeThread.set(null);
                // Call callback
                callback.run();
            }
        });

        if (activeThread.compareAndSet(null, newThread)) {
            newThread.start();
        } else {
            throw new IllegalStateException();
        }
    }

    public void destroy() {
        if (activeThread.get() != null) {
            activeThread.get().interrupt();
        }
        dispose();
    }

    private boolean checkIsInterrupted() {
        return activeThread.get().isInterrupted();
    }
}
