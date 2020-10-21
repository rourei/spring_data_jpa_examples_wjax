package de.mischok.academy.companydatabase.repository;

import de.mischok.academy.companydatabase.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> { }
