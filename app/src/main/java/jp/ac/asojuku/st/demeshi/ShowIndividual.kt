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
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_show_individual.*
import org.json.JSONObject

class ShowIndividual : AppCompatActivity() {
    var user_id = 0
    var card_id = 0
    val ImgArray = arrayOf(green, f4796,f4788,f4786,f4790,f4791,space,f4782,f4792)

    override fun onCreate(savedInstanceState: Bundle?) {
        user_id = intent.getIntExtra("UserId", 0)
        card_id = intent.getIntExtra("CardId", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_individual)
        BackBtn.setOnClickListener {
            val intent = Intent(this, MyCardList::class.java)
            intent.putExtra("UserId", user_id)
            startActivity(intent)
        }
        Delete.setOnClickListener { Delete() }
        getDetail()
    }
    fun Delete(){
        AlertDialog.Builder(this).apply {
            setTitle("名刺削除")
            setMessage("本当に名刺を削除しますか？")
            setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                // OKをタップしたときの処理
                val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/user/login"
                var user_id = 0
                Toast.makeText(context, "削除しました", Toast.LENGTH_LONG).show()
            })
            setNegativeButton("キャンセル", null)
            show()
        }
    }
    //名刺IDを表示
    //CardId.text = intent.getIntentExtra("CardId",0)
    fun getDetail(){
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/detail"
        URL.httpGet(listOf("meisi_id" to card_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val jsons = result.value.array()
                    val json = jsons[0] as JSONObject
                    var Templateid = 0
                    Name1.text = json.get("name").toString()
                    PhoneNumber.text=json.get("number").toString()
                    MailAddress.text=json.get("address").toString()
                    Templateid=json.get("img").toString().toInt()
                    MyCard1.setImageResource(ImgArray[Templateid-1])
                    SNS.text=(json.get("sns").toString())
                    CardId.setText(card_id.toString())
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}