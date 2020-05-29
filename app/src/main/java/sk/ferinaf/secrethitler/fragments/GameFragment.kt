package sk.ferinaf.secrethitler.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import sk.ferinaf.secrethitler.R
import sk.ferinaf.secrethitler.activities.GameActivity
import sk.ferinaf.secrethitler.activities.InvestigateActivity
import sk.ferinaf.secrethitler.activities.PeekPolicyActivity
import sk.ferinaf.secrethitler.activities.VotingActivity
import sk.ferinaf.secrethitler.common.GameState
import sk.ferinaf.secrethitler.common.PlayersInfo
import sk.ferinaf.secrethitler.widgets.PolicyCard

class GameFragment : Fragment() {

    enum class WelcomeDialog {
        FASCIST_ENACTED, LIBERAL_ENACTED, ELECTION_TRACKER, FASCISTS_WIN, LIBERALS_WIN
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_game, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        current_state_debug_button?.setOnClickListener {
            updateData()
        }

        var developing = false
        PlayersInfo.players.forEach {
            if (it.name == "developer") {
                developing = true
            }
        }

        button?.visibility = if (developing) View.VISIBLE else View.GONE
        button?.setOnClickListener {
            var toShow = ""
            PlayersInfo.players.forEach {
                toShow += it.name + ": " + it.role.toString() + "\n"
            }
            Toast.makeText(context, toShow, Toast.LENGTH_LONG).show()
        }
    }

    fun updateData() {
        val f = GameState.enactedFascist
        val l = GameState.enactedLiberal
        val v = GameState.electionTracker
        current_state_debug_test?.text = "F: $f, L: $l, tracker: $v"
    }

    fun presentWelcomeDialog(welcomeDialog: WelcomeDialog) {
        updateData()

        Log.d("state_welcome", welcomeDialog.toString())
        Toast.makeText(context, welcomeDialog.toString(), Toast.LENGTH_LONG).show()
    }

}