package com.tugas.deploy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "nim_users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String nama;

    private String nim;

    // Pastikan mapping nama kolom database (jenis_kelamin) sinkron dengan variabel Java
    @Column(name = "jenis_kelamin")
    private String jenisKelamin;
}