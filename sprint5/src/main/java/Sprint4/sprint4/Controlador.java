package Sprint4.sprint4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cobros")
public class Controlador {
	
	public static final int MULTIPLICADOR = 1000;
//	public static final int MULTIPLICADOR = 10;

	@Autowired
	private CobrosRepository cobros;

	public List<long[]> temporal;
	public List<ObjectId> index;

	public void load() {
		temporal = new ArrayList<long[]>();
		index = new ArrayList<ObjectId>();

		if (cobros.findAll() != null) {
			List<Cobros> temp = cobros.findAll();
			for (int i = 0; i < temp.size(); i++) {

				long[] nodo = { temp.get(i).getCliente(), temp.get(i).getCobro() };
				temporal.add(nodo);
				index.add(temp.get(i).get_ObjectId());
			}
		}
	}

	@RequestMapping("/")
	public String index() {
		return "Avengers Endgame fue terrible y horrible";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String indexGet() {
		return toString(temporal);
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String indexGetSpec(@PathVariable String id) {
		for (int i = 0; i < temporal.size(); i++) {
			if (temporal.get(i)[0] == Long.parseLong(id)) {
				return String.valueOf(temporal.get(i)[0]) + " ; " + String.valueOf(temporal.get(i)[1]) + "\n";
			}
		}
		return "No existe su bicho";
	}

//	@RequestMapping(value = "/post", method = RequestMethod.POST)
//	public String indexPost(@RequestBody Map<String, String> body) {
//		String cont = body.get("content");
//		String[] content = cont.split(";");
//		for (int i = 0; i < content.length; i++) {
//			boolean found = false;
//			for (int j = 0; j < temporal.size() && !found; j++) {
//				if (temporal.get(j)[0] == Long.parseLong(content[i])) {
//					temporal.get(j)[1] += MULTIPLICADOR;
//					found = true;
//					Cobros nodo = new Cobros(index.get(j), temporal.get(j)[0], temporal.get(j)[1]);
//					cobros.save(nodo);
//				}
//			}
//			if (!found) {
//				long[] nodo = { Long.parseLong(content[i]), MULTIPLICADOR };
//				temporal.add(nodo);
//				index.add(ObjectId.get());
//				Cobros nuevo = new Cobros(index.get((index.size() - 1)), Long.parseLong(content[i]), MULTIPLICADOR);
//				cobros.save(nuevo);
//			}
//		}
//		return "EXITO";
//	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String indexPost(@RequestBody Map<String, String> body) {
		String cont = body.get("content");
		String[] content = cont.split(":");
		for (int i = 0; i < content.length; i++) {
			String[] divid = content[i].split(";");
			boolean found = false;
			for (int j = 0; j < temporal.size() && !found; j++) {
				if (temporal.get(j)[0] == Long.parseLong(divid[0])) {
					temporal.get(j)[1] += Long.parseLong(divid[1]) * MULTIPLICADOR;
					if (temporal.get(j)[1] < 0) {
						temporal.get(j)[1] = 0;
					}
					found = true;
					Cobros nodo = new Cobros(index.get(j), temporal.get(j)[0], temporal.get(j)[1]);
					cobros.save(nodo);
				}
			}
			if (!found) {
				long[] nodo = { Long.parseLong(divid[0]), Long.parseLong(divid[1]) * MULTIPLICADOR };
				if (nodo[1] < 0) {
					nodo[1] = 0;
				}
				temporal.add(nodo);
				index.add(ObjectId.get());
				Cobros nuevo = new Cobros(index.get((index.size() - 1)), temporal.get((temporal.size() - 1))[0],
						temporal.get((temporal.size() - 1))[1]);
				cobros.save(nuevo);
			}
		}
		return "EXITO";
	}

	public String toString(List<long[]> cajas) {
		String resp = "CLIENTE ; COBRO" + "\n";
		for (int i = 0; i < cajas.size(); i++) {
			resp += String.valueOf(cajas.get(i)[0]) + " ; " + String.valueOf(cajas.get(i)[1]) + "\n";
		}
		return resp;
	}

	@RequestMapping(value = "/cobrar", method = RequestMethod.PUT)
	public String CobrarT() {
		String resp = "TOCA COBRAR: " + "\n" + toString(temporal) + "\n" + "TOCO MANDAR AVENGERS ENDGAME SPOILERS";
		for (int i = 0; i < temporal.size(); i++) {
			temporal.get(i)[1] = 0;
			Cobros nodo = new Cobros(index.get(i), temporal.get(i)[0], 0);
			cobros.save(nodo);
		}
		return resp;
	}

	@RequestMapping(value = "/cobrar/{id}", method = RequestMethod.PUT)
	public String Cobrar(@PathVariable String id) {
		for (int i = 0; i < temporal.size(); i++) {
			if (temporal.get(i)[0] == Long.parseLong(id)) {
				String resp = "Cobre: " + String.valueOf(temporal.get(i)[0]) + " ; "
						+ String.valueOf(temporal.get(i)[1]) + " o partale las patas!" + "\n";
				temporal.get(i)[1] = 0;
				Cobros nodo = new Cobros(index.get(i), temporal.get(i)[0], 0);
				cobros.save(nodo);
				return resp;
			}
		}
		return "no existe el bicho";
	}
}
