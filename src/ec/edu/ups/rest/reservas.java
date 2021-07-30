package ec.edu.ups.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.ReservaFacade;
import ec.edu.ups.ejb.RestauranteFacade;
import ec.edu.ups.entidades.Cliente;
import ec.edu.ups.entidades.Reserva;
import ec.edu.ups.entidades.Restaurante;


@Path("/reservas/")
public class reservas {

	@EJB
	private ClienteFacade ejbCliente;
	@EJB
	private ReservaFacade ejbReserva;
	@EJB
	private RestauranteFacade ejbResturante;

	private Jsonb jsonb;
	private Cliente cliente;
	private Restaurante restaurante;

	@POST
	@Path("/crearcliente")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCliente(@FormParam("cedula") String cedula, @FormParam("nombre") String nombre,
			@FormParam("apellido") String apellido, @FormParam("direccion") String direccion,
			@FormParam("telefono") String telefono, @FormParam("correo") String correo) {
		if (nombre != null && nombre.equals("") != true) {
			jsonb = JsonbBuilder.create();
			cliente = new Cliente(cedula, nombre, apellido, direccion, telefono, correo);
			try {
				ejbCliente.create(cliente);
				return Response.ok(jsonb.toJson(cliente)).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
			} catch (Exception e) {
				return Response.status(500).entity("Error al registrar usuario " + e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@POST
	@Path("/crearestaurante")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRestaurante(@FormParam("nombre") String nombre, @FormParam("direccion") String direccion,
			@FormParam("telefono") String telefono, @FormParam("aforo") int aforo) {
		if (nombre != null && nombre.equals("") != true) {
			jsonb = JsonbBuilder.create();
			restaurante = new Restaurante(nombre, direccion, telefono, aforo);
			try {
				ejbResturante.create(restaurante);
				return Response.ok(jsonb.toJson(restaurante)).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
			} catch (Exception e) {
				return Response.status(500).entity("Error al registrar el restaurante " + e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@POST
	@Path("/buscarCliente")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarCliente(@FormParam("cedula") String cedula) {

		if (cedula != null) {
			jsonb = JsonbBuilder.create();
			// cliente = ejbCliente.buscarPorCedula(cedula);

			try {
				cliente = ejbCliente.find(cedula);

				return Response.ok(jsonb.toJson(cliente)).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods", "*").build();
			} catch (Exception e) {
				return Response.status(500).entity("Error en la busqueda " + e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@POST
	@Path("/buscarRestaurante")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarRestaurante(@FormParam("nombre") String nombre) {

		if (nombre != null) {
			jsonb = JsonbBuilder.create();
			restaurante = ejbResturante.buscarPorNombre(nombre);

			try {
				return Response.ok(jsonb.toJson(restaurante)).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods", "*").build();
			} catch (Exception e) {
				return Response.status(500).entity("Error en la busqueda " + e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}


	
	@POST
	@Path("/reservas")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reservar(@FormParam("cedula") String cedula, @FormParam("nombre") String nombre,
			@FormParam("aforo") Integer aforo) {
		jsonb = JsonbBuilder.create();
		restaurante = ejbResturante.buscarPorNombre(nombre);
		cliente = ejbCliente.find(cedula);

		if (cedula != null && nombre != null) {
			Calendar c = new GregorianCalendar();
			Reserva re = new Reserva(c, aforo, cliente, restaurante);
			try {

				return Response.ok(jsonb.toJson(re)).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods", "*").build();
			} catch (Exception e) {
				return Response.status(500).entity("Error en la busqueda " + e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@POST
	@Path("/listarCliente")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response listar(@FormParam("cedula") String cedula) {

		Cliente cliente = ejbCliente.find(cedula);

		List<Reserva> pedido = new ArrayList<Reserva>();

		for (Reserva reserva : cliente.getReservasCliente()) {

			Cliente p = new Cliente(cliente.getCedula(), cliente.getNombre(), cliente.getApellido(),
					cliente.getTelefono(), cliente.getDireccion(), cliente.getCorreo());
			Reserva r = new Reserva(reserva.getId(), reserva.getFecha(), reserva.getNumeroPersonas(), p);

			pedido.add(r);
		}

		Jsonb jsonb = JsonbBuilder.create();
		return Response.status(201).entity(jsonb.toJson(pedido)).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}

	@POST
	@Path("/listarRestaurante")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response listarRest(@FormParam("nombre") String nombre) {

		Restaurante restuarante = ejbResturante.buscarPorNombre(nombre);

		List<Reserva> pedido = new ArrayList<Reserva>();

		for (Reserva pedidoCabecera : restuarante.getReservasRestaurante()) {

			Restaurante res = new Restaurante(pedidoCabecera.getRestauranteReserva().getId(),
					pedidoCabecera.getRestauranteReserva().getNombre(),
					pedidoCabecera.getRestauranteReserva().getDireccion(),
					pedidoCabecera.getRestauranteReserva().getTelefono(),
					pedidoCabecera.getRestauranteReserva().getAforo());
			Reserva r = new Reserva(pedidoCabecera.getId(), pedidoCabecera.getFecha(),
					pedidoCabecera.getNumeroPersonas(), res);

			pedido.add(r);
		}

		Jsonb jsonb = JsonbBuilder.create();
		return Response.status(201).entity(jsonb.toJson(pedido)).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}

	/*
	 * @POST
	 * 
	 * @Path("/cliente")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response createUsuario(String
	 * jsonUsuario) { System.out.
	 * println("entrando al metodo de crear usuario.............................. "
	 * ); jsonb = JsonbBuilder.create(); System.out.println("Usuario en registro " +
	 * jsonUsuario);
	 * 
	 * try { Cliente newUsuaio = jsonb.fromJson(jsonUsuario, Cliente.class);
	 * ejbCliente.create(newUsuaio);
	 * 
	 * return Response.ok().entity(newUsuaio) .header("Access-Control-Allow-Origin",
	 * "*") .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .allow("OPTIONS").build();
	 * 
	 * } catch (Exception e) {
	 * 
	 * return Response.status(500).entity("Usuario no creado: " + e)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .allow("OPTIONS").build();
	 * 
	 * } }
	 */
}
