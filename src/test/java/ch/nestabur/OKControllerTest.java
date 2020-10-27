package ch.nestabur;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;

@Import(ExtraConfig.class)
public class OKControllerTest extends BaseControllerTest  {

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

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @AutoConfigureWireMock(port = 0)
    class SecondControllerTest {

        @Test
        void test() {
            // arrange
            WireMock.stubFor(WireMock.get(WireMock.urlMatching("/find-all"))
                                     .willReturn(ResponseDefinitionBuilder.okForJson(Collections.singletonList(new Item("my-name")))));
        }

        @Nested
        // FIXME without importing the extra component, all test go through
        //@Import(ExtraConfig.class)
        class ThirdControllerTest extends BaseControllerTest {

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
    }


}
