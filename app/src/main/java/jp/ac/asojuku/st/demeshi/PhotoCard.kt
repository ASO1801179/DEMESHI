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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user_id = intent.getIntExtra("UserId",0)
        setContentView(R.layout.activity_photo_card)
        MyCardBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCardBtn.setOnClickListener{
            val intent = Intent(this,HaveCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        PhotoCardBtn.setOnClickListener{
            val intent = Intent(this,PhotoCard::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        AddBtn.setOnClickListener{add()}
    }
    fun add(){
        val card_id = Card_id.text.toString()
        val UserId = Pair("user_id", user_id.toString())
        val CardId = Pair("meisi_id", card_id)
        val pair = listOf<Pair<String,String>>(UserId,CardId)
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/collection"
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success->{
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        Toast.makeText(this,"追加しました。", Toast.LENGTH_LONG).show()
                    }
                }
                is Result.Failure -> {
                    Toast.makeText(this, "該当するIDは見つかりませんでした。", Toast.LENGTH_LONG).show()
                    println("通信に失敗しました。")
                }
            }
        }
    }
}