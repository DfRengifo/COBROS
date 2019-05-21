package Sprint4.sprint4;

import org.bson.types.ObjectId;

public class Cobros {

	public ObjectId _id;
	public long cliente;
	public long cobro;

	public Cobros() {
	}

	public Cobros(ObjectId _id, long cliente, long cobro) {
		this._id = _id;
		this.cliente = cliente;
		this.cobro = cobro;
	}

	public String get_id() {
		return _id.toHexString();
	}

	public ObjectId get_ObjectId() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public long getCliente() {
		return cliente;
	}

	public void setCliente(long cliente) {
		this.cliente = cliente;
	}

	public long getCobro() {
		return cobro;
	}

	public void setCobro(long cobro) {
		this.cobro = cobro;
	}
}
