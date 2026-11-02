package helpers;

import config.ConfigProvider;

import static io.restassured.RestAssured.given;

public class Browserstack {

    public static String videoUrl(String sessionId) {

        String url = String.format(
                "https://api-cloud.browserstack.com/app-automate/sessions/%s.json",
                sessionId
        );
        return given()
                .auth().basic(
                        ConfigProvider.CONFIG.userName(),
                        ConfigProvider.CONFIG.accessKey()
                )
                .get(url)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract()
                .path("automation_session.video_url");
    }
}