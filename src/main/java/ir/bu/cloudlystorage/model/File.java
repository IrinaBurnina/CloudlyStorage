package ir.bu.cloudlystorage.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"fileName", "login"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files", schema = "diploma")
public class File implements Serializable {
    @Column(name = "filename")
    @EmbeddedId
    private String fileName;
    @Column
    private Long size;
    @Column(name = "file", columnDefinition = "bytea")
    @Lob
    private byte[] content;
    @Column(name = "filepath")
    private String filePath;
    @EmbeddedId
    @JoinColumn
    @ManyToOne
    private String login;
}
