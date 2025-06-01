package com.example.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.integrador.entities.Clasificacion;

public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer>{
// hace select, delate, delect all, seelect por id, insert
}
