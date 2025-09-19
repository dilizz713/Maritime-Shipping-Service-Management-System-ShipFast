package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, Long> {
}
