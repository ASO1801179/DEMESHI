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
        BackBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        Delete.setOnClickListener{Delete()}
        getDetail()
    }
    fun Delete(){
        AlertDialog.Builder(this).apply {
            setTitle("名刺削除")
            setMessage("本当に名刺を削除しますか？")
            setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                // OKをタップしたときの処理

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
                    Name1.text = (json[0] as JSONObject).get("value").toString()
                    PhoneNumber.text = (json[3] as JSONObject).get("value").toString()
                    MailAddress.text = (json[1] as JSONObject).get("value").toString()
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
