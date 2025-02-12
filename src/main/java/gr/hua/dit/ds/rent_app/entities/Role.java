package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;

/*Entity for roles of users*/
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id of role

    @Column(length = 20)
    private String name;//name of role

    //Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Constructors and toString
    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
