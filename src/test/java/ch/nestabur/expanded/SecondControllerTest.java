package ch.nestabur.expanded;

import ch.nestabur.Item;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class SecondControllerTest {

    @Test
    void test() {
        // arrange
        WireMock.stubFor(WireMock.get(WireMock.urlMatching("/find-all"))
                                 .willReturn(ResponseDefinitionBuilder.okForJson(Collections.singletonList(new Item("my-name")))));
    }

}