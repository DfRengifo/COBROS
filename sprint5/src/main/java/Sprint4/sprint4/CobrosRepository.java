package Sprint4.sprint4;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CobrosRepository extends MongoRepository<Cobros, String> {
	Cobros findBy_id(ObjectId _id);
}
