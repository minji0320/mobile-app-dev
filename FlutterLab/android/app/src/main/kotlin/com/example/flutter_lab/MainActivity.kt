package com.example.flutter_lab

import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        val channel = BasicMessageChannel<String>(flutterEngine.dartExecutor,
            "myMessageChannel",
            StringCodec.INSTANCE)

        channel.setMessageHandler { message, reply ->
            Log.d("msg", "receive: $message")
            // dart에 결과 리턴
            reply.reply("reply from Android")

            // platform -> dart ...
            channel.send("Hello, I am platform message... android") {
                Log.d("msg", "reply: $it")
            }
        }

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,
            "myMethodChannel").setMethodCallHandler { call, result ->
            if (call.method == "parameterMap") {
                val map = call.arguments as Map<String, String>
                result.success(mapOf("one" to 10, "two" to 20))
            } else {
                result.notImplemented()
            }
        }

        val eventChannel = EventChannel(flutterEngine.dartExecutor, "eventChannel")
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            // dart에서 자신을 listener로 등록하는 순간 호출됨
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                events?.success("send event data... from native...")
            }

            override fun onCancel(arguments: Any?) {

            }

        })

    }
}
