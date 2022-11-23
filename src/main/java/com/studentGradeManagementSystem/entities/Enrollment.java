package com.studentGradeManagementSystem.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "enrollment")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int semester;

    private Long studentId;

    private int subjectId;

    private String approvalId;

    private String currentlyEnrolled;


}
