import java.awt.Desktop;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class specialDouble {
    public static void main(String[] args) throws Exception {
        String page = """
                      <!doctype html>
                      <html>
                      <head><meta charset="UTF-8"><title>You Are Special</title>
                      <style>
                        body { margin: 0; min-height: 100vh; display: grid; place-items: center; background: #fff4c2; font-family: sans-serif; text-align: center; }
                        h1 { color: #159447; font-size: 3rem; }
                        .monkeys { font-size: 4rem; line-height: 1.5; max-width: 700px; }
                      </style></head>
                      <body><main><h1>You are very special!</h1>
                      <div class="monkeys">\ud83d\udc12 \ud83d\udc12 \ud83d\udc12 \ud83d\udc12 \ud83d\udc12<br>\ud83d\udc12 \ud83d\udc12 \ud83d\udc12 \ud83d\udc12 \ud83d\udc12</div></main></body>
                      </html>
                      """;

        Path file = Files.createTempFile("special-monkeys-", ".html");
        Files.write(file, page.getBytes(StandardCharsets.UTF_8));

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(file.toUri().toString()));
        } else {
            System.out.println("Open this file in a browser: " + file.toUri());
        }
    }
}