package shakespearesearch.backendserver;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ShakespeareController {

    @GetMapping("/shakespeare")
    public ResponseEntity<String> getShakespeareText() throws IOException {
        Resource resource = new ClassPathResource("/shakespeare.txt");
        String text = new String(Files.readAllBytes(resource.getFile().toPath()));
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

}
