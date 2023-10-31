package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputBinding
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.play.setOnClickListener{
            create()

        }
    }

    fun create(){
        GameData.saveGameModel(
            GameModel(
                gamestatus = GameStatus.JOINED
            )
        )
        start()

    }

    fun start(){
        startActivity(Intent(this,GameActivity::class.java))

    }


}