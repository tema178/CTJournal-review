package ctjournal;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CTJApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CTJApplication.class);
        Console.main(args);
    }
}
