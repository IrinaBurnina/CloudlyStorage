package ir.bu.cloudlystorage.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files", schema = "diploma")
public class File implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(name = "filename", nullable = false)
    private String fileName;
    private long size;
    @Column(name = "file", columnDefinition = "bytea")
    @Lob
    private byte[] content;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private CloudUser user;
    private LocalDate data;
}
