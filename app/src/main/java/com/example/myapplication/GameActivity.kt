package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityGameBinding

    private var gameModel : GameModel? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGame.setOnClickListener{
            startGame()
        }
        GameData.gameModel.observe(this){
            gameModel=it
            setUI()
        }

    }
    fun setUI(){
        gameModel?.apply {
            binding.btn0.text =filledpos[0]
            binding.btn1.text =filledpos[1]
            binding.btn2.text =filledpos[2]
            binding.btn3.text =filledpos[3]
            binding.btn4.text =filledpos[4]
            binding.btn5.text =filledpos[5]
            binding.btn6.text =filledpos[6]
            binding.btn7.text =filledpos[7]
            binding.btn8.text =filledpos[8]

            binding.startGame.visibility = View.VISIBLE

            binding.status.text=
                when(gamestatus){
                    GameStatus.CREATED ->{
                        binding.startGame.visibility = View.INVISIBLE
                        "Game ID :"+gameid

                    }
                    GameStatus.JOINED ->{
                        "Click On Start Game"
                    }
                    GameStatus.INPROGRESS ->{
                        binding.startGame.visibility = View.INVISIBLE
                        currentplayer + " turn"
                    }
                    GameStatus.FINISHED ->{
                        if(winner.isNotEmpty()) winner + " WON"
                        else "DRAW"

                    }
                }
        }
    }


    fun startGame(){
        gameModel?.apply {
            updateGameData(
                GameModel(
                    gameid=gameid,
                    gamestatus=GameStatus.INPROGRESS
                )
            )

        }

    }
    fun updateGameData(model: GameModel){
        GameData.saveGameModel(model)
    }

    fun checkWinner(){
        val winnerPos = arrayOf(
            intArrayOf(0,1,2),
            intArrayOf(3,4,5) ,
            intArrayOf(6,7,8) ,

            intArrayOf(0,3,6) ,
            intArrayOf(1,4,7),
            intArrayOf(2,5,8),

            //diognal
            intArrayOf(0,4,8),
            intArrayOf(2,4,6)
        )

        gameModel?.apply {
            for (i in winnerPos) {

                if (
                    filledpos[i[0]] == filledpos[i[1]] &&
                    filledpos[i[1]] == filledpos[i[2]] &&
                    filledpos[i[0]].isNotEmpty()

                ){
                    gamestatus =GameStatus.FINISHED
                    winner =filledpos[i[0]]
                }
            }
            if (filledpos.none(){it.isEmpty()} ){
                gamestatus= GameStatus.FINISHED
            }
            updateGameData(this)
        }
    }

    override fun onClick(p0: View?) {
        gameModel?.apply {
            if (gamestatus!= GameStatus.INPROGRESS){
                Toast.makeText(applicationContext,"Game not started", Toast.LENGTH_SHORT).show()
                return
            }
            //game is started
            val clickedpos =(p0?.tag as String).toInt()
            if(filledpos[clickedpos].isEmpty()){
                filledpos[clickedpos]=currentplayer
                currentplayer=if(currentplayer=="X") "O" else "X"
                checkWinner()
                updateGameData(this)
            }
        }

    }

}