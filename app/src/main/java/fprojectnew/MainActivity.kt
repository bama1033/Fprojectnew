package com.android.projectf.fprojectnew

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.academy.fprojectnew.datamodel.Scene_model
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var scoreHome = 0
    private var scoreAway = 0
    private var gameTimer = 0
    private val contentList = ArrayList<Scene_model>()
    private var timeToken = 0
    private var time = 0
    private var gameOverToken = false
/*
        TODO -Implementing Spielverlauf ✔
        TODO -Implementing 0..90 ✔
        TODO -Implementing Logic(PlayerStats, Strategy, Tactics, Moral)
        TODO -Buttons mit Actions versehen
        TODO -Teamselector


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
        setContentView(R.layout.new_main)
        setTeamNames(getString(R.string.team_dortmund), getString(R.string.team_bayern))
        startGame()
        val rv = findViewById<RecyclerView>(R.id.game_course)
        var adapter = ListAdapter(contentList)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //addScene(getString(R.string.scene_beginning, getString(R.string.team_dortmund), getString(R.string.team_bayern)))
        rv.adapter = adapter

//        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                rv.smoothScrollToPosition(adapter.itemCount-1)
//            }
//        })
        delayMainStart(adapter, rv)
    }


    private fun setTeamNames(team_name_home: String, team_name_two: String) {
        setViewText(team_home, team_name_home)
        setViewText(team_away, team_name_two)

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
        createTime()
        addScene(getString(R.string.scene_beginning, getString(R.string.team_dortmund), getString(R.string.team_bayern)))

        //TODO who has ball??team1..2
        //pass shoot midfield attack defense
        //sceneevent,sceneaction,sceneresult
        //Entweder PassiveView seite 1 Ballbesitz team1 -->Spielaufbau -->Mittelfeld-->Sturm
        //oder random situationen nur mittelfeld/angriff
    }

    private fun createTime() {
        when (gameOverToken) {
            false -> {
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
                checkTimer(gameTimer + time)
            }
            else -> {
                endGame()
            }
        }
    }

    private fun checkTimer(timer: Int) {
        //check halftime aswell
        when (timer) {
            in 88..90 -> {
                gameTimer = 90
                gameOverToken = true
            }
            in 91..96 -> {
                time = 93
                gameOverToken = true
            }
            else -> {
                gameTimer = timer
            }

        }
    }

    private fun addScene(content: String) {
        contentList.add(Scene_model(gameTimer.toString(), content))
        updateTime()
    }

    private fun updateTime() {
        game_clock.text = gameTimer.toString()

    }

    private fun startGame() {
        game_clock.text = gameTimer.toString()
    }

    private fun endGame() {}

    private fun setViewText(view: TextView, text: String) {
        view.text = text
    }

    private fun delayMainStart(adapter: ListAdapter, rv: RecyclerView) {

        Handler().postDelayed({
            this.runOnUiThread {
                if (!gameOverToken) {
                    createScene()
                    adapter.notifyDataSetChanged()
                    rv.smoothScrollToPosition(adapter.itemCount - 1)
                    //Let's change background's color from blue to red.
//                    val color = arrayOf(ColorDrawable(Color.WHITE), ColorDrawable(Color.RED))
//                    val trans = TransitionDrawable(color)
//                    //This will work also on old devices. The latest API says you have to use setBackground instead.
//                    button_one.setBackgroundDrawable(trans)
//                    trans.startTransition(5000)

                    delayMainStart(adapter, rv)
                }
            }
        }, 2000)
    }

}
