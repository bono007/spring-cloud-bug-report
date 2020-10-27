package ch.nestabur.expanded;

import ch.nestabur.BaseControllerTest;
import ch.nestabur.ExtraConfig;
import ch.nestabur.Item;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;

@Import(ExtraConfig.class)
class FirstControllerTest extends BaseControllerTest {

    @Test
    void test() {
        // arrange
        WireMock.stubFor(WireMock.get(WireMock.urlMatching("/find-all"))
                                 .willReturn(ResponseDefinitionBuilder.okForJson(Collections.singletonList(new Item("my-name")))));

        // act
        List<Item> responseBody = webTestClient.get().uri("find-all")
                                               .exchange().expectStatus().is2xxSuccessful()
                                               .expectBody(new ParameterizedTypeReference<List<Item>>() {}).returnResult().getResponseBody();

        // assert
        Assertions.assertThat(responseBody.get(0).getName()).isEqualTo("my-name");
    }

}