package org.mw.holgithub.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "game")
data class GameModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(targetEntity = UserModel::class)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserModel,

    @OneToOne(targetEntity = GameStateModel::class, optional = true, cascade = [CascadeType.ALL])
    @JoinColumn(name = "game_state_id", nullable = true)
    var gameState: GameStateModel?,

    @Column(name = "score", nullable = false)
    var score: Int,
)