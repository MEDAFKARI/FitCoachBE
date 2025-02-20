package com.fitcoach.fitcoach.dtos;

import com.fitcoach.fitcoach.dao.entity.Programme;
import com.fitcoach.fitcoach.enums.Equipment;
import com.fitcoach.fitcoach.enums.Goal;
import com.fitcoach.fitcoach.enums.Level;
import com.fitcoach.fitcoach.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ClientDTO {

    private Long id;

    private String firstName;

    private String lastName;
    private String email;

    private String avatar;

    private Date createdAt;
    private Role role;

    private Date updateAt;

    private CoachDTO coach;

    private Integer age;

    private Equipment equipment;

    private Goal goal;

    private Level level;

    private Integer days_per_week;

    private ProgrammeDTO programme;


}
