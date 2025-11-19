package com.example.Painting.CommandPattern;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    OpType type;

    @Lob
    String oldState ;

    @Lob
    String newState;

    boolean undo;








}
