package com.ceos.springvote22nd.entity;

import com.ceos.springvote22nd.entity.enums.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User voter;

    @Enumerated(EnumType.STRING)
    private Team team;

    @Builder
    public TeamVote(User voter, Team team) {
        this.voter = voter;
        this.team = team;
    }
}