package jp.ac.asojuku.st.demeshi

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_show_my_card.*
import org.json.JSONObject

class ShowMyCard : AppCompatActivity() {

    var user_id = 0
    var card_id = 0
    var company_id = ""
    val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)

    override fun onCreate(savedInstanceState: Bundle?) {
        user_id = intent.getIntExtra("UserId",0)
        card_id = intent.getIntExtra("CardId",0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_card)
        BackBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        Delete.setOnClickListener{Delete()}
        getDetail()
        Handler().postDelayed(Runnable{
            getCompany()
        },1500)

        check.setOnClickListener {
            val intent = Intent(this, ShowCompany::class.java)
            intent.putExtra("Flag","My")
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",card_id)
            intent.putExtra("company_id", company_id.toString())
            startActivity(intent)
        }
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
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/detail"

        URL.httpGet(listOf("meisi_id" to card_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val jsons = result.value.array()
                    val json = jsons[0] as JSONObject
                    var Templateid = 0
                    Name1.text = "名前：" + json.get("name").toString()
                    name1.text = json.get("name").toString()
                    Mail.text = "メール：" + json.get("address").toString()
                    mail.text = json.get("address").toString()
                    Phone.text = "電話番号：" + json.get("number").toString()
                    phone.text = json.get("number").toString()
                    CardId.text = "名刺ID：" + card_id.toString()
                    Templateid=json.get("img").toString().toInt()
                    MyCard1.setImageResource(ImgArray[Templateid-1])
                    company_id = json.get("company_id").toString()
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
    fun getCompany(){
        val URL2:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/getData"
        URL2.httpGet(listOf("company_id" to company_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")
                    if (results == 1) {
                        Company.text = "企業：" + json.get("company_name").toString()
                        place.text = json.get("company_name").toString()
                    }else{
                        println("失敗")
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
