package drumre.projekt.tvapp.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.AuthProvider;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String email;
    private String password;

    @NotNull
    private Provider provider;

    @Column(name = "auth_provider_id")
    private String authProviderID;

    @Column(name = "image_url")
    private String imageUrl;
}
