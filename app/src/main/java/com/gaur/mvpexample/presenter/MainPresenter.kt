package com.gaur.mvpexample.presenter

import com.gaur.mvpexample.contracts.MainActivityContract
import com.gaur.mvpexample.launchOnMain
import com.gaur.mvpexample.network.model.UniversityDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainPresenter(
    private val view:MainActivityContract.View,
    private val model:MainActivityContract.Model
) : MainActivityContract.Presenter , MainActivityContract.Model.OnFinishListener{

    val scope = CoroutineScope(Dispatchers.IO)

    override fun getUniversity(country: String) {
        scope.launch {
            model.fetchUniversity(onFinishListener = this@MainPresenter,country = country)
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }


    override fun onLoading() {
        scope.launchOnMain { view.onLoading() }

    }

    override fun onError(message: String) {
        scope.launchOnMain { view.onError(message)}
    }

    override fun onSuccess(list: List<UniversityDTO>) {
        scope.launchOnMain {view.onSuccess(list)}
    }
}