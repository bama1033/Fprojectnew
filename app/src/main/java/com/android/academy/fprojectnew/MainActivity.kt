package com.android.academy.fprojectnew

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.academy.fprojectnew.datamodel.Scene_model
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private var home_team = "home_team"
    private var away_team = "away_team"
    private var scoreHome = 0
    private var scoreAway = 0
    private var gameTimer = 0
    private val contentList = ArrayList<Scene_model>()
    private var timeToken = 0
    private var gameOverToken = false
    private lateinit var adapter: ListAdapter
    private lateinit var rv: RecyclerView
/*
        TODO -Implementing Spielverlauf ✔
        TODO -Implementing 0..90 ✔
        TODO States(wo sind wir im Feld)
        TODO MakeTeams (GK Def MFs,Atk und suche einen random pro Scene aus)
        TODO -Actions
        TODO -Implementing Logic(PlayerStats, Strategy, Tactics, Moral)
        TODO -Buttons mit Actions versehen
        TODO -Teamselector UI ✔


        TODO -Mainmenu(welcomescreen, modeselect, highscore, exit)
        TODO -Gamemenu
        TODO -Spielgeschwindigkeit 1x, 2x

        TODO -adding Teams  ---> API?
        TODO -adding Players ---> API?
        TODO -Animationen für actions
        TODO -Firebase-Anbindung um Highscore zu speichern
        TODO -Gamemodes (Create a Team_model, Online Competitive)
 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTeams(getString(R.string.team_dortmund), getString(R.string.team_bayern))


        rv = findViewById(R.id.game_course)
        adapter = ListAdapter(contentList)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.adapter = adapter
        startGame()
        delayMainStart(adapter, rv)
    }

    private fun setTeams(team_name_home: String, team_name_two: String) {
        setViewText(team_home, team_name_home)
        setViewText(team_away, team_name_two)
        setImage(image_home, R.drawable.borussia_dortmund)
        setImage(image_away, R.drawable.bayern_muenchen)
    }

    fun setImage(view: ImageView, text: Int) {
        view.setImageResource(text)
    }

    private fun updateScore(team: String) {
        when (team) {
            "home" -> {
                scoreHome++
                setViewText(counter_home, scoreHome.toString())
            }
            "away" -> {
                scoreAway++
                setViewText(counter_away, scoreAway.toString())
            }
        }
    }

    private fun createScene() {
        createTime(getString(R.string.scene_pass))
        //TODO who has ball??team1..2
        //pass shoot midfield attack defense
        //sceneevent,sceneaction,sceneresult
        //Entweder PassiveView seite 1 Ballbesitz team1 -->Spielaufbau -->Mittelfeld-->Sturm
        //oder random situationen nur mittelfeld/angriff
    }

    private fun createTime(string: String) {
//        when (gameOverToken) {
//            true -> {
//                endGame()
//            }
//            false -> {
        var time: Int
        when (timeToken) {
            0, 1 -> {
                time = (2..5).shuffled().first()
                timeToken += 1
            }
            else -> {
                time = (6..9).shuffled().first()
                timeToken = 0
            }
        }
        checkTimer(gameTimer + time, string)
    }

    private fun checkTimer(timer: Int, string: String) {
        //check halftime aswell
        when (timer) {
            in 88..90 -> {
                gameTimer = 90
                gameOverToken = true
                endGame()

            }
            in 91..96 -> {
                gameTimer = 93
                gameOverToken = true
                endGame()
            }
            else -> {
                gameTimer = timer
                addScene(string)
            }

        }
    }

    private fun addScene(content: String) {
        contentList.add(Scene_model(gameTimer.toString(), content))
        updateTime()
        updateAdapter()
    }

    private fun updateTime() {
        game_clock.text = gameTimer.toString()

    }

    private fun startGame() {
        game_clock.text = gameTimer.toString()
        addScene(getString(R.string.scene_beginning, getString(R.string.team_dortmund), getString(R.string.team_bayern)))

    }

    private fun endGame() {
        addScene(getString(R.string.scene_ending))
    }

    private fun setViewText(view: TextView, text: String) {
        view.text = text
    }

    private fun updateAdapter() {
        adapter.notifyDataSetChanged()
        rv.smoothScrollToPosition(adapter.itemCount - 1)
    }

    private fun delayMainStart(adapter: ListAdapter, rv: RecyclerView) {
        Handler().postDelayed({
            this.runOnUiThread {
                if (!gameOverToken) {
                    createScene()
                    //Let's change background's color from blue to red.
//                    val color = arrayOf(ColorDrawable(Color.WHITE), ColorDrawable(Color.RED))
//                    val trans = TransitionDrawable(color)
//                    //This will work also on old devices. The latest API says you have to use setBackground instead.
//                    button_one.setBackgroundDrawable(trans)
//                    trans.startTransition(5000)
                    delayMainStart(adapter, rv)
                }
                //   endGame()
            }
        }, 2000)
    }

}
