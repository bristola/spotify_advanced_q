package repos;

import data.Queue;
import java.util.List;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {
    List<Queue> findAll();
}
