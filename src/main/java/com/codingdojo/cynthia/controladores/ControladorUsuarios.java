package com.codingdojo.cynthia.controladores;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.cynthia.modelos.Salon;
import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.servicios.Servicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller //Encargada de decir que mi archivo es un controlador. Regresar el archivo jsp
public class ControladorUsuarios {
	
	@Autowired
	private Servicio serv;

	@GetMapping("/") //Creo la ruta raíz para mi proyecto
	public String index() {
		return "index.jsp"; //Da como respuesta el archivo index.jsp
	}
	
	@GetMapping("/inicio")
	public String inicio(Model model) {
		//Model me permite enviar información de la función hacia el jsp
		
		model.addAttribute("titulo", "¡Bienvenid@ al inicio de JSTL");
		
		String usuarios[] = {"Elena de Troya", "Juana de Arco", "Pablo Picasso", "Pedro Páramo"};
		model.addAttribute("listaUsuarios", usuarios);
		
		HashMap<String, String> paises = new HashMap<String, String>();
		paises.put("Mexico", "CDMX");
		paises.put("El Salvador", "San Salvador");
		paises.put("Argentina", "Buenos Aires");
		
		model.addAttribute("paises", paises);
		
		return "inicio.jsp";
	}
	
	// /holi/Elena
	@GetMapping("/holi/{nombre}")
	public String holi( @PathVariable("nombre") String name,
						Model model) {
		//name = Elena
		model.addAttribute("nombre_persona", name);
		
		
		return "holi.jsp";
	}
	
	//3 rutas para el formulario
	//1.- Muestra Formulario
	@GetMapping("/formulario")
	public String formulario() {
		return "formulario.jsp";
	}
	
	//2.- Recibe formulario
	@PostMapping("/registro")
	public String registro(@RequestParam(value="nombre") String nombresito,
						   @RequestParam(value="email") String email,
						   HttpSession session, /*Permite guardar en sesión*/
						   RedirectAttributes flash /*Permite enviar errores*/ ) {
		
		System.out.println("El nombre del usuario es:"+nombresito);
		System.out.println("El email del usuario es:"+email);
		
		//Validamos la info
		if(nombresito.equals("") && email.equals("")) {
			flash.addFlashAttribute("error", "Por favor proporciona tu nombre y email");
			return "redirect:/formulario";
		}
		
		if(nombresito.equals("")) {
			flash.addFlashAttribute("errorNombre", "Por favor ingresa tu nombre");
			return "redirect:/formulario";
		}
		
		if(email.equals("")) {
			flash.addFlashAttribute("errorEmail", "Por favor ingresa tu correo");
			return "redirect:/formulario";
		}
		
		session.setAttribute("nombreUsuario", nombresito);
		session.setAttribute("emailUsuario", email);
		
		/*Object contadorObjeto = session.getAttribute("contador");
		if(contadorObjeto == null) { //Atributo en sesión NO EXISTE
			
		}
		
		session.invalidate(); //Borra todos los datos de sesión
		session.removeAttribute("contador");
		
		*/
		
		
		return "redirect:/bienvenida"; //redirect manda a una ruta
	}
	
	//3.- Redirección
	@GetMapping("/bienvenida")
	public String bienvenida() {
		return "bienvenida.jsp";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		List<Usuario> usuarios = serv.todosUsuarios();
		model.addAttribute("usuarios", usuarios);
		return "dashboard.jsp";
	}
	
	@GetMapping("/nuevo")
	public String nuevo(@ModelAttribute("usuario") Usuario usuario,
						Model model /*Enviar info de mi metodo al jsp*/ ) {
		
		List<Salon> salones = serv.todosSalones(); //Creo la lista de salones
		model.addAttribute("salones", salones); //crea variable salones para ser enviada a jsp
		
		//@ModelAttribute crea objeto vacío de Usuario y lo manda a nuevo.jsp
		return "nuevo.jsp";
	}
	
	@PostMapping("/crear") //@Valid me permite validar la info del objeto
	public String crear( @Valid @ModelAttribute("usuario") Usuario usuarioNuevo,
						BindingResult result,
						Model model) { /*Encargado de enviar los msg valid*/
		if(result.hasErrors()) {
			List<Salon> salones = serv.todosSalones(); //Creo la lista de salones
			model.addAttribute("salones", salones); //crea variable salones para ser enviada a jsp
			return "nuevo.jsp"; //return a jsp
		} else {
			serv.guardarUsuario(usuarioNuevo);
			return "redirect:/dashboard"; //redirect
		}
	}
	
	@DeleteMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") Long idABorrar) {
		serv.borrarUsuario(idABorrar);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id,
						 @ModelAttribute("usuario") Usuario usuario,
						 Model model) { /*Enviar info a jsp*/
		//Obtener el objeto de usuario que quiero editar
		Usuario usuarioBuscado = serv.buscarUsuario(id);
		model.addAttribute("usuario", usuarioBuscado);
		return "editar.jsp";
	}
	
	@PutMapping("/actualizar/{id}") //IMPORTANTE: debe llamarse id
	public String actualizar(@Valid @ModelAttribute("usuario") Usuario usuarioEditado,
							 BindingResult result) {
		
		if(result.hasErrors()) {
			return "editar.jsp";
		} else {
			serv.guardarUsuario(usuarioEditado);
			return "redirect:/dashboard";
		}
		
	}
}







