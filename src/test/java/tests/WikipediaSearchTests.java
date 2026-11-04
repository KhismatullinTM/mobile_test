package tests;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

@DisplayName("Тесты приложения Wikipedia")
public class WikipediaSearchTests extends TestBase {

    @DisplayName("Успешный поиск статей в Wikipedia")
    @Test
    void successfulSearchTest() {
        step("Открыть поиск Wikipedia", () ->
                $(AppiumBy.accessibilityId("Search Wikipedia"))
                        .shouldBe(visible)
                        .click());

        step("Ввести в поиск: QA engineer", () ->
                $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"))
                        .shouldBe(visible)
                        .sendKeys("QA engineer"));

        step("Проверить, что список результатов поиска не пустой", () ->
                $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @DisplayName("Открытие статьи из результатов поиска Wikipedia")
    @Test
    void openArticleTest() {
        step("Открыть поиск Wikipedia", () ->
                $(AppiumBy.accessibilityId("Search Wikipedia"))
                        .shouldBe(visible)
                        .click());
        step("Ввести в поиск: QA engineer", () ->
                $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"))
                        .shouldBe(visible)
                        .sendKeys("QA engineer"));
        step("Проверить наличие результатов поиска", () ->
                $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
        step("Получить название первой статьи и открыть её", () -> {
            var firstArticle = $$(AppiumBy.id(
                    "org.wikipedia.alpha:id/page_list_item_title"
            )).first();
            firstArticle.shouldBe(visible);
            String articleTitle = firstArticle.getText();
            firstArticle.click();
            step("Проверить открытие статьи: " + articleTitle, () ->
                    $(AppiumBy.androidUIAutomator(
                            "new UiSelector().text(\"" + articleTitle + "\")"
                    )).shouldBe(visible));
        });
    }

    @DisplayName("Проверка экранов онбординга")
    @Test
    void successfulOnboardingProcessed() {
        step("Проверить отображение первого экрана онбординга", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"All the world's knowledge\")"
                )).shouldBe(visible));

        step("Перейти ко второму экрану", () ->
                $(AppiumBy.accessibilityId("Forward")).click());

        step("Проверить отображение экрана Data & Privacy", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Data & Privacy\")"
                )).shouldBe(visible));

        step("Перейти к третьему экрану", () ->
                $(AppiumBy.accessibilityId("Forward")).click());

        step("Проверить отображение экрана Read in more than 300 languages", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Read in more than 300 languages\")"
                )).shouldBe(visible));

        step("Перейти к четвёртому экрану", () ->
                $(AppiumBy.accessibilityId("Forward")).click());

        step("Проверить отображение экрана Follow your curiosity", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Follow your curiosity\")"
                )).shouldBe(visible));

        step("Перейти к пятому экрану", () ->
                $(AppiumBy.accessibilityId("Next")).click());

        step("Проверить отображение экрана What are you interested in?", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"What are you interested in?\")"
                )).shouldBe(visible));

        step("Перейти к шестому экрану", () ->
                $(AppiumBy.accessibilityId("Next")).click());

        step("Проверить отображение экрана What would you like to see first?", () ->
                $(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"What would you like to see first?\")"
                )).shouldBe(visible));

        step("Завершить прохождение онбординга", () ->
                $(AppiumBy.accessibilityId("Next")).click());
    }
}