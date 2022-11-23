package com.studentGradeManagementSystem.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exams")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exams {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int marksObtained;

    private Long studentId;

    private Long paperId;

    private int semester;

}
