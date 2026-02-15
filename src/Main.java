//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar secret-letter-censor.jar <input.txt> <output.txt>");
            System.exit(1);
        }


        Path input = Path.of(args[0]);
        Path output = Path.of(args[1]);

        try {
            String text = Files.readString(input, StandardCharsets.UTF_8);
            String censored = new Censor().censor(text);

            Files.writeString(output, censored, StandardCharsets.UTF_8);
            System.out.println("Done. Output: " + output.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(2);
        }
    }
}
