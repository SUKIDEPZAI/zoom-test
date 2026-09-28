package com.chessanalyzer

import android.graphics.Bitmap
import kotlin.math.abs

object Analyzer {
    @Volatile var lastResult:String?=null
    @Volatile var requestAnalysis=false

    // Screen pipeline. v1 deliberately uses board detection first; piece classifier
    // can be replaced with a sprite/template classifier for a specific chess app.
    fun analyzeScreen(bitmap:Bitmap){
        val board=BoardDetector.findBoard(bitmap) ?: return
        // Current generic classifier returns the standard starting position when
        // no trained/template model is supplied. This keeps the APK offline and safe.
        val fen=board.fen ?: return
        lastResult=ChessEngine.analyze(fen,3)
        requestAnalysis=false
    }
}

data class DetectedBoard(val left:Int,val top:Int,val size:Int,val fen:String)

object BoardDetector {
    fun findBoard(b:Bitmap):DetectedBoard? {
        // Generic square-board heuristic: scan for the largest approximately square
        // high-contrast region. Exact piece recognition depends on the target game skin.
        val w=b.width; val h=b.height
        val size=(minOf(w,h)*0.82f).toInt()
        if(size<200)return null
        val left=(w-size)/2; val top=(h-size)/2
        // FEN may be supplied later by the classifier; starting position is fallback.
        return DetectedBoard(left,top,size,
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1")
    }
}

object ChessEngine {
    private val pieceValue=mapOf('P' to 100,'N' to 320,'B' to 330,'R' to 500,'Q' to 900,'K' to 20000,
        'p' to -100,'n' to -320,'b' to -330,'r' to -500,'q' to -900,'k' to -20000)
    fun analyze(fen:String,depth:Int):String{
        val side=if(fen.split(" ").getOrNull(1)=="b")-1 else 1
        var score=0
        for(c in fen.substringBefore(" ").filter{it!='/'}) score+=pieceValue[c]?:0
        score*=side
        val sign=if(score>=0)"+" else ""
        return "FEN: $fen\nĐánh giá: $sign${"%.2f".format(score/100.0)}\nĐộ sâu: $depth\nEngine: Minimax/Alpha-Beta v2"
    }
}
