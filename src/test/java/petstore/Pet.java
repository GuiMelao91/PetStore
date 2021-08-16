// 1 - package do projeto
package petstore;


//2 - Blibliotecas usadas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // Enderereço da entidade Pet


    //3.2 - Metodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //incluir - create Post
    @Test (priority = 1) //Identifica o metódo ou função como um teste para o Testng (priority = 1)
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
                .statusCode(200)
                .body("name",is("acerola")) // Validar se no jSON tem o nome cadastrado
                .body("status",is("available"))
                //.body("id",is(9223372036854775807L))
                .body("id",is(19911012))
                .body("category.name",is("Dog")) // coletando dados de dentro da categoria do json
                .body("tags.name",contains("sta"))   // coletando dados de dentro da categoria do json com []

        ;
    }
    @Test (priority = 2)
    public void consultarPet(){
        String petId = "19911012";
        String token =
        given()
                .contentType("application/json") //leia o arquivo
                .log().all()
        .when()
                .get(uri + "/" + petId)    // concatena om / para fazer o get
        .then()
                .log().all()                     // pega o log e verifica se esta 200
                .statusCode(200)
                .body("category.name",is("Dog"))
                .body("status",is("available"))
        .extract()
                .path("category.name")

        ;
        System.out.println("O token é validado :"  + token);
    }
    @Test (priority = 3)
    public void alterarPet() throws IOException {          //put
        String jsonBody = lerJson("json/pet2.json" );

    given()
            .contentType("application/json")
            .log().all()
            .body(jsonBody)
    .when()
            .put(uri)
    .then()
            .log().all()
            .statusCode(200)
            .body("status",is("Solded"))
            .body("name",is("acerola"))
    ;
    }
    @Test (priority = 4)
    public void excluirPet(){       //Excluir - delete
        String petId = "19911012";

    given()
            .contentType("application/json")
            .log().all()
    .when()
            .delete(uri + "/" + petId)
    .then()
            .log().all()
            .statusCode(200)
            .body("code",is(200))
            .body("type",is("unknown"))
            .body("message",is(petId))
    ;


    }

}
