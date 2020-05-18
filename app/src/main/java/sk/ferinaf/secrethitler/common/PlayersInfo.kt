package sk.ferinaf.secrethitler.common

import sk.ferinaf.secrethitler.models.GovernmentRole
import sk.ferinaf.secrethitler.models.Player
import sk.ferinaf.secrethitler.models.Roles
import java.util.*
import kotlin.collections.ArrayList

object PlayersInfo {

    private var players: ArrayList<Player> = arrayListOf()


    // Set names of players and assign them secret roles
    fun setNames(players: ArrayList<String>) {

        players.forEach { name ->
            this.players.add(Player(name))
        }

        // Randomly pick hitler position
        val hitlerPosition = (0 until getPlayersCount()).random()
        this.players[hitlerPosition].role = Roles.HITLER

        // Add fascists depending on player count (1st, 2nd, 3rd ... )
        addNthFascist(1)

        if (getPlayersCount() > 6) {
            addNthFascist(2)
        }

        if (getPlayersCount() > 8) {
            addNthFascist(3)
        }

    }


    // Help function for assigning roles of fascists
    private fun addNthFascist(n: Int) {
        // Number of non fascist players
        val fascistPos = (0 until getPlayersCount() -n).random()
        var counter = -1
        var index = -1
        while (counter != fascistPos){
            index += 1
            if (players[index].role == Roles.LIBERAL) {counter += 1}
        }

        players[index].role = Roles.FASCIST
    }


    // Get array of players (hitler and fascists for other fascist)
    // for i-th player to show in role assigning process
    fun getRevealedFascists(playerIndex: Int): ArrayList<Player?> {

        val otherFascists = getFascists()
        otherFascists.remove(players[playerIndex])

        // one hitler, one fascist other are liberals
        if (getPlayersCount() <= 6 && players[playerIndex].role == Roles.HITLER) {
            return arrayListOf(null, otherFascists[0], null)
        }

        // return when player is fascist
        if (players[playerIndex].role == Roles.FASCIST) {
            when (players.size) {
                5 -> return arrayListOf(getHitler(), null, null)
                6 -> return arrayListOf(getHitler(), null, null)
                7 -> return arrayListOf(getHitler(), otherFascists[0], null)
                8 -> return arrayListOf(getHitler(), otherFascists[0], null)
                9 -> return arrayListOf(getHitler(), otherFascists[0], otherFascists[1])
                10 -> return arrayListOf(getHitler(), otherFascists[0], otherFascists[1])
            }
        }

        return arrayListOf(null, null, null)
    }


    fun getPlayersCount(): Int {
        return players.size
    }

    fun getFascists(): ArrayList<Player> {
        val fascists: ArrayList<Player> = arrayListOf()

        for (i in 0 until players.size) {
            if (players[i].role == Roles.FASCIST) {
                fascists.add(players[i])
            }
        }

        return fascists
    }


    fun getHitler(): Player {
        return players.first { player ->
            player.role == Roles.HITLER
        }
    }


    fun getPlayer(i: Int): Player {
        return players[i]
    }


    fun isPlayerFascist(i: Int): Boolean {
        return players[i].role != Roles.LIBERAL
    }

    fun initPresident() {
        players[0].governmentRole = GovernmentRole.PRESIDENT
    }

    fun getPresident(): Player? {
        return players.firstOrNull { player ->
            player.governmentRole == GovernmentRole.PRESIDENT
        }
    }

    fun getChancellor(): Player? {
        return players.firstOrNull { player ->
            player.governmentRole == GovernmentRole.CHANCELLOR
        }
    }

}