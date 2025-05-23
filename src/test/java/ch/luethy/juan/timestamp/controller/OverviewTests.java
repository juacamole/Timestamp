package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.TimestampApplication;
import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.repository.StampRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@SpringBootTest(classes = TimestampApplication.class)
public class OverviewTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StampRepository stampRepository;

    @Test
    public void getStampsShouldReturnEmptyList() throws Exception {

        String accessToken = obtainAccessToken();

        mockMvc.perform(get("/overview").header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

    @Test
    public void getStampsShouldReturnListOfStamps() throws Exception {
        Stamp stamp = new Stamp();
        stampRepository.save(stamp);

        String accessToken = obtainAccessToken();

        mockMvc.perform(get("/overview").header("Authorization", "Bearer " + accessToken)
                .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"id\":1,\"time\":null,\"user\":null}]")));


    }

    private String obtainAccessToken() {

        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=Timestamp&" +
                "grant_type=password&" +
                "scope=openid profile roles offline_access&" +
                "username=test" +
                "password=test";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = rest.postForEntity("http://localhost:8080/realms/Timestamp/protocol/openid-connect/token", entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }

}
