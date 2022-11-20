package ru.practicum.exploreWithMe.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
//    Проверить нужна ли валидация или параметры приходят уже отвалидированные

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
