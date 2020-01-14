package de.luka.api.nummern;

import org.springframework.data.repository.CrudRepository;

import de.luka.api.nummern.Nummer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NummerRepository extends CrudRepository<Nummer, Long> {

}