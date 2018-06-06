package io.rest.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import io.rest.springboot.model.Configuration;
public interface ConfigurationRepository extends JpaRepository<Configuration,Integer> {

}
