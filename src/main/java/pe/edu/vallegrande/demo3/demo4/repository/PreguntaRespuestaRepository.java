package pe.edu.vallegrande.demo3.demo4.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import pe.edu.vallegrande.demo3.demo4.model.PreguntaRespuesta;

public interface PreguntaRespuestaRepository extends MongoRepository<PreguntaRespuesta, String> {
}
