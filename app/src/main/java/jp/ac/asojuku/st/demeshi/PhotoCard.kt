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
import kotlinx.android.synthetic.main.activity_photo_card.*

class PhotoCard : AppCompatActivity() {

    var user_id = 0
    var card_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user_id = intent.getIntExtra("UserId",0)
        card_id = intent.getIntExtra("CardId",0)
        setContentView(R.layout.activity_photo_card)
        MyCardBtn.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        HaveCardBtn.setOnClickListener{startActivity(Intent(it.context,HaveCardList::class.java))}
        PhotoCardBtn.setOnClickListener{startActivity(Intent(it.context,PhotoCard::class.java))}
        AddBtn.setOnClickListener{add()}
    }
    fun add(){
        val URL:String = "http://18001187.pupu.jp/untitled/public/user/card/insert/"+user_id.toString() + "/" + card_id.toString()
        URL.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Success->{
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        Toast.makeText(this,"追加しました。", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "該当するIDは見つかりませんでした。", Toast.LENGTH_LONG).show()
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}