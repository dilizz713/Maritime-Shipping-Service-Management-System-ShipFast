package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String contactPerson;
    private String address;
    private String mobileNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

   /* @OneToOne
    @JoinColumn(name = "user_id")
    private User user;*/
}
