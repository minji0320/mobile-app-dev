package com.example.ch4

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ch4.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var connectionMode = "none" // ui 제어

    lateinit var messenger: Messenger
    lateinit var replyMessenger: Messenger

    // 프로그래스바를 지속적으로 변경하기 위해 - 코루틴 사용
    var messengerJob: Job? = null

    // AIDL
    var aidlService: MyAIDLInterface? = null
    var aidlJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // messenger
        onCreateMessengerService()

        // aidl
        onCreateAIDLService()

        // jobservice
        onCreateJobScheduler()
    }

    override fun onStop() {
        super.onStop()
        if (connectionMode == "messenger") {
            onStopMessengerService()
        } else if (connectionMode == "aidl") {
            onStopAIDLService()
        }
        connectionMode = "none"
        changeViewEnabled()
    }

    private fun changeViewEnabled() = when (connectionMode) {
        "messenger" -> {
            binding.messengerPlay.isEnabled = false
            binding.aidlPlay.isEnabled = false
            binding.messengerStop.isEnabled = true
            binding.aidlStop.isEnabled = false
        }
        "aidl" -> {
            binding.messengerPlay.isEnabled = false
            binding.aidlPlay.isEnabled = false
            binding.messengerStop.isEnabled = false
            binding.aidlStop.isEnabled = true
        }
        else -> {
            binding.messengerPlay.isEnabled = true
            binding.aidlPlay.isEnabled = true
            binding.messengerStop.isEnabled = false
            binding.aidlStop.isEnabled = false

            // 프로그래스 초기화
            binding.messengerProgress.progress = 0
            binding.aidlProgress.progress = 0
        }
    }

    inner class HandlerReplyMsg : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                10 -> {
                    // 재생 시간 전달
                    val bundle = msg.obj as Bundle
                    bundle.getInt("duration")?.let {
                        when {
                            it > 0 -> {
                                binding.messengerProgress.max = it

                                // 코루틴으로 지속적으로 프로그래스바 값 변경
                                val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
                                messengerJob = backgroundScope.launch {
                                    // 코루틴 빌더, 코루틴 구동, 리턴값은 Job!
                                    while (binding.messengerProgress.progress < binding.messengerProgress.max) {
                                        delay(1000)
                                        binding.messengerProgress.incrementProgressBy(1000)
                                    }
                                }
                                changeViewEnabled()
                            }
                            else -> {
                                connectionMode = "none"
                                unbindService(messengerConnection)
                                changeViewEnabled()
                            }
                        }
                    }
                }
            }
        }
    }

    // bindService에 이용할 ServiceConnection
    val messengerConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            messenger = Messenger(p1)
            val msg = Message()
            msg.replyTo = replyMessenger
            msg.what = 10
            messenger.send(msg)
            connectionMode = "messenger"
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

    }

    private fun onCreateMessengerService() {
        replyMessenger = Messenger(HandlerReplyMsg())

        binding.messengerPlay.setOnClickListener {
            val intent = Intent("ACTION_SERVICE_MESSENGER")
            intent.setPackage("com.example.ch4_outer")
            bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE)
        }

        binding.messengerStop.setOnClickListener {
            onStopMessengerService()
        }
    }

    private fun onStopMessengerService() {
        val msg = Message()
        msg.what = 20
        messenger.send(msg)
        unbindService(messengerConnection)
        messengerJob?.cancel()
        connectionMode = "none"
        changeViewEnabled()
    }

    // aidl
    val aidlConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            // 두번째 매개변수로 전달된 객체가 외부 앱에서 onBind()에서 리턴한 Stub
            aidlService = MyAIDLInterface.Stub.asInterface(p1)
            aidlService?.let {
                it.start()
                binding.aidlProgress.max = it.maxDuration
            }

            val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
            aidlJob = backgroundScope.launch {
                while (binding.aidlProgress.progress < binding.aidlProgress.max) {
                    delay(1000)
                    binding.aidlProgress.incrementProgressBy(1000)
                }
            }
            connectionMode = "aidl"
            changeViewEnabled()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            aidlService = null
        }
    }

    private fun onCreateAIDLService() {
        binding.aidlPlay.setOnClickListener {
            val intent = Intent("ACTION_SERVICE_AIDL")
            intent.setPackage("com.example.ch4_outer")
            bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
        }

        binding.aidlStop.setOnClickListener {
            onStopAIDLService()
        }
    }

    private fun onStopAIDLService() {
        aidlService!!.stop()
        unbindService(aidlConnection)
        aidlJob?.cancel()
        connectionMode = "none"
        changeViewEnabled()
    }

    private fun onCreateJobScheduler() {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        //job 이 실행되는 조건....
        //어떤 서비스가 실행되어야 한다는 조건 들어갔다..
        //1 이 개발자가 주는 식별자.. 나중에 이 식별자로.. 등록 취소 시킬 수 있다..
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)//wifi network....
        val job = builder.build()
        //등록...
        scheduler!!.schedule(job)
    }
}