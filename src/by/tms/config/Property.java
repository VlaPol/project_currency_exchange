package by.tms.config;

import java.nio.file.Path;
import java.util.Objects;

public class Property {

    private final Path fileDirectory;
     public Property(Path fileDirectory) {
        this.fileDirectory = Objects.requireNonNull(fileDirectory);
    }

    public Path getFileDirectory() {
        return fileDirectory;
    }
}
