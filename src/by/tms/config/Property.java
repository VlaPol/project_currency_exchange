package by.tms.config;

import java.nio.file.Path;
import java.util.Currency;
import java.util.Objects;

public class Property {

    private final Path fileDirectory;
    private final Currency code;

    public Property(Path fileDirectory, Currency code) {
        this.fileDirectory = Objects.requireNonNull(fileDirectory);
        this.code = Objects.requireNonNull(code);
    }

    public Path getFileDirectory() {
        return fileDirectory;
    }

    public String getCurrencyCode() {
        return code.getCurrencyCode();
    }
}
