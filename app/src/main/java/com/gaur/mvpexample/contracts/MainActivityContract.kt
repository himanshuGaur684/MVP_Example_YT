package com.gaur.mvpexample.contracts

import com.gaur.mvpexample.network.model.UniversityDTO

interface MainActivityContract {

    interface View{
        fun onLoading()
        fun onSuccess(list:List<UniversityDTO>)
        fun onError(message:String)
    }

    interface Presenter{
        fun getUniversity(country:String)
        fun onDestroy()
    }


    interface Model{
        interface OnFinishListener{
            fun onLoading()
            fun onError(message:String)
            fun onSuccess(list:List<UniversityDTO>)
        }
        suspend fun fetchUniversity(onFinishListener: OnFinishListener,country:String)
    }
}