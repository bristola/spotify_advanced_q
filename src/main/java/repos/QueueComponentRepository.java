package repos;

import data.QueueComponent;
import java.util.List;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueComponentRepository extends CrudRepository<QueueComponent, Long> {
    List<QueueComponent> findAll();
}
