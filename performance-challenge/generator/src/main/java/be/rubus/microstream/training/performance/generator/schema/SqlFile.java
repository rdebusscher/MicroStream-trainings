package be.rubus.microstream.training.performance.generator.schema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SqlFile {

    private List<String> statements;

    public SqlFile(String filePath) throws IOException {
        parseFile(filePath);
    }

    private void parseFile(String filePath) throws IOException {
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        statements = Arrays.asList(fileContent.split(";"));
    }

    public List<String> getStatements() {
        return statements;
    }
}
