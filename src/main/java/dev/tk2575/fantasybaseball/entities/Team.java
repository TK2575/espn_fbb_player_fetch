package dev.tk2575.fantasybaseball.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team
{

    String abbreviation;
    int id;
    String location;
    String displayName;
    String nickname;
    Roster roster;
}
