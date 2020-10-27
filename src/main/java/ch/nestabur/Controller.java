package ch.nestabur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class Controller {

    private final WebClient webClient;

    @Value("${base-url}")
    private String baseUrl;

    public Controller(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("find-all")
    public Mono<List<Item>> findAll() {
        return webClient
                   .get()
                   .uri(baseUrl + "/find-all")
                   .retrieve().bodyToMono(new ParameterizedTypeReference<List<Item>>() {});
    }

}
