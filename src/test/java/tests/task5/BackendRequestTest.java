package tests.task5;

import api.task5.models.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class BackendRequestTest {

    @Test
    @DisplayName("Получить список пользователей со страницы 2")
    public void getUsers() {
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserSchema.json"))
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);

        // Проверка полученных данных
        assertThat(users).extracting(UserData::getId).isNotNull();
        assertThat(users).extracting(UserData::getFirst_name).contains("Tobias");
        assertThat(users).extracting(UserData::getLast_name).contains("Funke");
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/users/2")
    public void getUser() {
        UserData user = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserData.json"))
                .extract().jsonPath().getObject("data", UserData.class);

        // Проверка данных пользователя
        assertThat(user).extracting(UserData::getId).isEqualTo(2);
        assertThat(user).extracting(UserData::getEmail).isEqualTo("janet.weaver@reqres.in");
        assertThat(user).extracting(UserData::getFirst_name).isEqualTo("Janet");
        assertThat(user).extracting(UserData::getLast_name).isEqualTo("Weaver");
        assertThat(user).extracting(UserData::getAvatar).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/users/22")
    public void getUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/22")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/unknown")
    public void getResources() {
        List<ColorData> resources = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorDataList.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", ColorData.class);

        // Проверка данных ресурсов
        assertThat(resources).extracting(ColorData::getId).isNotNull();
        assertThat(resources).extracting(ColorData::getName).contains("cerulean");
        assertThat(resources).extracting(ColorData::getYear).contains(2000);
        assertThat(resources).extracting(ColorData::getColor).contains("#98B2D1");
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/unknown/2")
    public void getResource() {
        ColorData resource = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorSchema.json"))
                .extract().jsonPath().getObject("data", ColorData.class);

        // Проверка данных ресурса

        assertThat(resource).extracting(ColorData::getId).isEqualTo(2);
        assertThat(resource).extracting(ColorData::getName).isEqualTo("fuchsia rose");
        assertThat(resource).extracting(ColorData::getYear).isEqualTo(2001);
        assertThat(resource).extracting(ColorData::getColor).isEqualTo("#C74375");
        assertThat(resource).extracting(ColorData::getPantone_value).isEqualTo("17-2031");
    }

    @Test
    @DisplayName("Проверка эндпоинта /api/unknown/25")
    public void getResourceNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/25")
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Создание пользователя через эндпоинт /api/users POST")
    public void createUser() {
        RequestUser requestUser = RequestUser.builder()
                .name("Polina")
                .job("Developer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateUser.json"))
                .extract().as(ResponseUser.class);

        // Проверка данных созданного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Обновление пользователя через эндпоинт /api/users/2 PUT")
    public void updateUserPut() {
        RequestUser requestUser = RequestUser.builder()
                .name("Polina")
                .job("Developer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserUpdate.json"))
                .extract().as(ResponseUser.class);

        // Проверка данных обновленного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Частичное обновление пользователя через эндпоинт /api/users/2 PATCH")
    public void updateUserPatch() {
        RequestUser requestUser = RequestUser.builder()
                .name("Polina")
                .job("Java Developer")
                .build();

        ResponseUser responseUser = given()
                .contentType(ContentType.JSON)
                .body(requestUser)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserUpdate.json"))
                .extract().as(ResponseUser.class);

        // Проверка данных частично обновленного пользователя
        assertThat(responseUser).isNotNull();
        assertThat(responseUser).extracting(ResponseUser::getName).isEqualTo(requestUser.getName());
        assertThat(responseUser).extracting(ResponseUser::getJob).isEqualTo(requestUser.getJob());
    }

    @Test
    @DisplayName("Удаление пользователя через эндпоинт /api/users/2 DELETE")
    public void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Успешная регистрация через эндпоинт /api/register")
    public void registerSuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SuccessfulRegisterResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка данных успешной регистрации
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getId).isEqualTo(4);
        assertThat(response).extracting(RegisterLoginResponse::getToken).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешная регистрация через эндпоинт /api/register")
    public void registerUnsuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("test@mail")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginRegisterUnsuccessfulResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка данных неуспешной регистрации
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getError).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Успешный логин через эндпоинт /api/login")
    public void loginSuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SuccessfulLoginResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка данных успешного логина
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getToken).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Неуспешный логин через эндпоинт /api/login")
    public void loginUnsuccessful() {
        RegisterLoginRequest request = RegisterLoginRequest.builder()
                .email("test@mail")
                .build();

        RegisterLoginResponse response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginRegisterUnsuccessfulResponse.json"))
                .extract().as(RegisterLoginResponse.class);

        // Проверка данных неуспешного логина
        assertThat(response).isNotNull();
        assertThat(response).extracting(RegisterLoginResponse::getError).isEqualTo("Missing password");
    }

    @Test
    @DisplayName("Получение списка пользователей с задержкой через эндпоинт /api/users?delay=3")
    public void getUsersDelay() {
        List<UserData> users = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .time(greaterThan(3000L)).and().time(lessThan(6000L))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ListUserSchema.json"))
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .extract().jsonPath().getList("data", UserData.class);

        // Проверка данных полученных пользователей
        assertThat(users).extracting(UserData::getId).contains(2);
        assertThat(users).extracting(UserData::getFirst_name).contains("Janet");
        assertThat(users).extracting(UserData::getLast_name).contains("Weaver");
    }
}
