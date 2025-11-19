package com.example.Painting.Repository;

import com.example.Painting.CommandPattern.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command,Long> {
}
