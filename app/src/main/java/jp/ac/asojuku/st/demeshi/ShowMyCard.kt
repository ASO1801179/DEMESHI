package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_show_my_card.*
import org.json.JSONObject

class ShowMyCard : AppCompatActivity() {

    var user_id = 0
    var card_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        user_id = intent.getIntExtra("UserId",0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_card)
        BackBtn.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        Delete.setOnClickListener{Delete()}
        getDetail()
    }
    fun Delete(){
        AlertDialog.Builder(this).apply {
            setTitle("名刺削除")
            setMessage("本当に名刺を削除しますか？")
            setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                // OKをタップしたときの処理
                val URL:String = "http://18001187.pupu.jp/untitled/public/user/login"
                var user_id = 0
//                URL.httpGet(pair).responseJson() { request, response, result ->
//                    when (result) {
//                        is Result.Success -> {
//                            // レスポンスボディを表示
//                            val json = result.value.obj()
//                            val results = json.get("result")// as JSONArray
//                            if(results == 1){
//                                user_id = json.get("user_id").toString().toInt()
//                                Toast.makeText(this, "ログインしました", Toast.LENGTH_LONG).show()
//                            }
//                        }
//                        is Result.Failure -> {
//                            println("通信に失敗しました。")
//                        }
//                    }
//                }
                Toast.makeText(context, "削除しました", Toast.LENGTH_LONG).show()
            })
            setNegativeButton("キャンセル", null)
            show()
        }
    }
    //名刺IDを表示
    //CardId.text = intent.getIntentExtra("CardId",0)
    fun getDetail(){
        val URL:String = "http://18001187.pupu.jp/untitled/public/card/infomation/" + card_id.toString()
        URL.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    for(i in 0..json.length()){

                    }
//                    val jsona = json[0] as JSONObject
//                    print(jsona.get("meisi_id"))
//                    println("!!")
//                    val json = result.value.obj()
//                    val results = json.get("result")// as JSONArray
//                    if(results == 1){
//                        //取得したもので処理
//                        //名刺ごとの名刺IDをintentでShowMyCardに渡す
//                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
