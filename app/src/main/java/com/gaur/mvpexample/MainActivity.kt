package com.gaur.mvpexample

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gaur.mvpexample.adapter.MainAdapter
import com.gaur.mvpexample.contracts.MainActivityContract
import com.gaur.mvpexample.databinding.ActivityMainBinding
import com.gaur.mvpexample.model.MainModel
import com.gaur.mvpexample.network.api.ApiService
import com.gaur.mvpexample.network.model.UniversityDTO
import com.gaur.mvpexample.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var presenter: MainPresenter

    private var _binding:ActivityMainBinding?=null
    private val binding:ActivityMainBinding
    get() = _binding!!
    private val mainAdapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this, MainModel(apiService))

        initView()

        binding.searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
              return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               presenter.getUniversity(newText.toString())
                return true
            }
        })

    }

    private fun initView() {
        binding.rvMVP.adapter = mainAdapter
    }


    override fun onLoading() {
        binding.progress.visibility= View.VISIBLE
    }

    override fun onSuccess(list: List<UniversityDTO>) {
        binding.progress.visibility= View.GONE
        mainAdapter.addItems(list)
    }

    override fun onError(message: String) {
        binding.progress.visibility= View.GONE
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}