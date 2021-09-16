package app.repository;

import app.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository  extends JpaRepository<CarEntity,String> {
}
