package com.alavpa.logger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_log.clearFab
import kotlinx.android.synthetic.main.activity_log.logList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


class LogActivity : AppCompatActivity() {
    private var observableLog: Observable<String>? = null
    private var observableClear: Observable<String>? = null
    private val logAdapter = LogAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val pid = android.os.Process.myPid()
        val commandLog = "logcat --pid $pid -v time -d"
        val commandClear = "logcat -c"

        observableLog = Observable.create {
            execLine(commandLog, it)
        }

        observableClear = Observable.create {
            execLine(commandClear, it)
        }

        logList.layoutManager = LinearLayoutManager(this)
        logList.adapter = logAdapter

        clearFab.setOnClickListener {

            logAdapter.clear()
            observableClear?.let { observable ->
                val disposable = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        observableLog?.let { observable ->
            val disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { buffer ->
                    logAdapter.add(buffer)
                }
            compositeDisposable.add(disposable)
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun execLine(command: String, emitter: ObservableEmitter<String>) {
        var bufferedReader: BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec(command)
            bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            var line = bufferedReader.readLine()
            while (line != null) {
                emitter.onNext(line)
                line = bufferedReader.readLine()
            }
        } catch (e: Throwable) {
            emitter.onError(e)
        } finally {
            bufferedReader?.close()
        }

        emitter.onComplete()
    }
}
