package ru.pavlovap.sshexample.check;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Start {

    public static final String ORIGINAL = "src/main/resources/old.log";
    public static final String RIVISED = "src/main/resources/new.log";

    public static void main(String[] args) throws DiffException, IOException {

        List<String> original = Files.readAllLines(new File(ORIGINAL).toPath());
        List<String> revised = Files.readAllLines(new File(RIVISED).toPath());

        String string = DiffUtils.diff(original, revised).getDeltas().toString();

        System.out.println(string);
    }
}
