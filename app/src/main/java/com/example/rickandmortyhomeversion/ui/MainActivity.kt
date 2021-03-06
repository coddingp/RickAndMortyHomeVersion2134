package com.example.rickandmortyhomeversion.ui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyhomeversion.api.InterfaceApi
import com.example.rickandmortyhomeversion.api.RetrofitClient
import com.example.rickandmortyhomeversion.databinding.ActivityMainBinding
import com.example.rickandmortyhomeversion.models.CharacterDataResponse
import com.example.rickandmortyhomeversion.models.ResultResponse
import timber.log.Timber

class MainActivity : AppCompatActivity(), InterfaceContract {

    private val api: InterfaceApi = RetrofitClient.getRetrofit().create(InterfaceApi::class.java)

    private val activityPresenter = ActivityPresenter(api)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var characterData: CharacterDataResponse

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.treeCount
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(onClick = { sendDataToSecondActivity(it) })
        binding.mainRecyclerView.adapter = mainAdapter

        activityPresenter.attach(this)
        activityPresenter.getCharacterDataFromApi()

    }

    override fun showCharacters(data: CharacterDataResponse) {
        mainAdapter.setData(data.resultsResponse)
    }

    override fun dataFailure(t: Throwable) {
        Timber.e(t.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        activityPresenter.detach()
    }

    private fun sendDataToSecondActivity(resultsResponse: ResultResponse) {
        val intent = Intent(this@MainActivity, DetailedActivity::class.java)
        intent.putExtra("result", resultsResponse)
        startActivity(intent)
    }
}