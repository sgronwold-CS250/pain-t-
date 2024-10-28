import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * The webserver that broadcasts the current autosave.png image
 */
public class WebServer implements HttpHandler {
    HttpServer server;

    public WebServer() throws IOException {
        // begin stack overflow code to make the web server
        server = HttpServer.create(new InetSocketAddress(42069), 0);
        server.createContext("/", this);
        server.setExecutor(null); // creates a default executor
        server.start();
        // end stack overflow code to make the web server
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        // begin stack overflow code to serve webpage
            Headers headers = he.getResponseHeaders();
            headers.add("Content-Type", "image/png");
            
            File file = new File ("../autosave.png");
            byte[] bytes  = new byte [(int)file.length()];
            System.out.println(file.getAbsolutePath());
            System.out.println("length:" + file.length());
            
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(bytes, 0, bytes.length);
            bufferedInputStream.close();

            he.sendResponseHeaders(200, file.length());
            OutputStream outputStream = he.getResponseBody();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.close();
        // end stack overflow code to serve webpage
    }
}
