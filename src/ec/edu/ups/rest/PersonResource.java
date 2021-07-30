package ec.edu.ups.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.modelo.Person;

@Path("/cliente/")
public class PersonResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCliente(@PathParam("id") Integer id) {
	return "User [" + id + ",PathParam]";
    }

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    public String parametros(@DefaultValue("666") @QueryParam("id") Integer id,
	    @DefaultValue("666,deamon") @QueryParam("person") Person u) {
	return "Id: " + id + "\n" + u.toString();
    }

    // Ejemplo con JSON

    @GET
    @Path("/query2/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
	Jsonb jsonb = JsonbBuilder.create();
	Person person = new Person(id, "Pepito", "pepito@test.com", 20, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(523.7));
	return Response.ok(jsonb.toJson(person)).build();
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response post(@FormParam("id") int id, @FormParam("name") String name) throws IOException {
	Person person = new Person(id, name);
	System.out.println("REST/client:post-->" + person);
	return Response.ok("Cliente creado correctamente!").build();
    }

    @PUT
    @Path("/put")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJSON(String jsonPerson) {
	Jsonb jsonb = JsonbBuilder.create();
	Person person = jsonb.fromJson(jsonPerson, Person.class);

	System.out.println("REST/client:putJSON-->" + person);
	return Response.status(204).entity("Usuario actualizaado..." + person).build();
    }

    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
	System.out.println("REST/client:delete-->" + id);
	return Response.status(204).entity("Usuario borrado..." + id).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPersons() {
	
	Jsonb jsonb = JsonbBuilder.create();
	List<Person> list = new ArrayList<Person>();
	Person person1= new Person(1, "Pepito", "pepito@test.com", 20, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(123.7));
	Person person2 = new Person(2, "Juanito", "juanito@test.com", 21, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(223.1));
	Person person3 = new Person(3, "Jaimito", "jaimito@test.com", 22, LocalDate.of(2020, 6, 30),
		BigDecimal.valueOf(323.4));
	list.add(person1);
	list.add(person2);
	list.add(person3);
	
	// para evitar el error del CORS se agregan los headers
	return Response.ok(jsonb.toJson(list))
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
    }
}
