package com.chessanalyzer

import android.app.*
import android.content.*
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.*

class OverlayService: Service() {
    private lateinit var wm: WindowManager
    private lateinit var bubble: TextView
    private var panel: LinearLayout?=null

    override fun onCreate() {
        super.onCreate()
        wm=getSystemService(WINDOW_SERVICE) as WindowManager
        bubble=TextView(this).apply {
            text="☰"; textSize=28f; setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(35,105,55)); gravity=17
            setPadding(18,10,18,10)
            setOnClickListener { toggle() }
        }
        val p=WindowManager.LayoutParams(
            62,62,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
        p.gravity=Gravity.TOP or Gravity.END; p.y=220
        wm.addView(bubble,p)
    }
    private fun toggle(){
        if(panel!=null){ wm.removeView(panel);panel=null;return }
        panel=LinearLayout(this).apply {
            orientation=LinearLayout.VERTICAL; setPadding(22,18,22,18)
            setBackgroundColor(Color.rgb(22,27,34))
            val title=TextView(context).apply{text="♟ CHESS ANALYZER v2";textSize=18f;setTextColor(Color.WHITE)}
            val result=TextView(context).apply{
                text=Analyzer.lastResult ?: "Đang chờ nhận diện bàn cờ…"
                textSize=16f;setTextColor(Color.rgb(190,235,200));setPadding(0,20,0,20)
            }
            addView(title);addView(result)
            addView(Button(context).apply{text="Phân tích lại";setOnClickListener{
                Analyzer.requestAnalysis=true; result.text=Analyzer.lastResult ?: "Đang phân tích…"
            }})
            addView(Button(context).apply{text="Đóng";setOnClickListener{toggle()}})
        }
        val p=WindowManager.LayoutParams(
            360,WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT)
        p.gravity=Gravity.TOP or Gravity.END;p.y=290
        wm.addView(panel,p)
    }
    override fun onDestroy(){if(::bubble.isInitialized)wm.removeView(bubble);panel?.let{wm.removeView(it)};super.onDestroy()}
    override fun onBind(i:Intent?):IBinder?=null
}
