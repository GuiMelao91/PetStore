// 1 - package do projeto
package petstore;


//2 - Blibliotecas usadas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

//3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // Enderereço da entidade Pet


    //3.2 - Metodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //incluir - create Post
    @Test  //Identifica o metódo ou função como um teste para o Testng
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("json/pet1.json");

        //  BDD SINTAXE GHERKIN
        // GIVEN - WHEN - THEN
        given() // dado
                .contentType("application/json") // comum em API REST - aintigos era""text/xm
                .log().all()
                .body(jsonBody)
        .when()  //Quando
                .post(uri)
        .then()  // Então
                .log().all()
                .statusCode(200);
    }

}
