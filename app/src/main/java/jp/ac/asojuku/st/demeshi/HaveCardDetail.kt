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
import kotlinx.android.synthetic.main.activity_have_card_detail.*
import jp.ac.asojuku.st.demeshi.R.drawable.*
import org.json.JSONObject

class HaveCardDetail : AppCompatActivity() {

    var user_id = 0
    var card_id = 0
    var company = ""
    val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_detail)
        user_id = intent.getIntExtra("UserId", 0)
        card_id = intent.getIntExtra("CardId", 0)

        Back.setOnClickListener {
            val intent = Intent(this, HaveCardList::class.java)
            intent.putExtra("UserId", user_id)
            startActivity(intent)
        }
        Delete.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("名刺削除")
                setMessage("本当に名刺を削除しますか？")
                setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->
                    // OKをタップしたときの処理
                })
                setNegativeButton("キャンセル    ", null)
                show()
            }
        }
        getData1()
        Handler().postDelayed(Runnable{
            getData2()
        },1000)

        check.setOnClickListener {
            val intent = Intent(this, ShowCompany::class.java)
            intent.putExtra("company_id", company.toString())
            intent.putExtra("Flag","Have")
            startActivity(intent)
        }
    }
    fun Delete(): Boolean {
        var bool = true
        return bool
    }

    fun getData1(){
        val URL: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/detail"
        URL.httpGet(listOf("meisi_id" to card_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    company = (json[0] as JSONObject).get("company_id").toString()
                    Name1.text = "名前：" + (json[0] as JSONObject).get("name").toString()
                    name1.text = (json[0] as JSONObject).get("name").toString()
                    Phone.text = "電話番号" + (json[0] as JSONObject).get("number").toString()
                    phone.text = (json[0] as JSONObject).get("number").toString()
                    Mail.text = "メールアドレス：" + (json[0] as JSONObject).get("address").toString()
                    mail.text = (json[0] as JSONObject).get("address").toString()
                    CardId.text = "名刺ID：" + (json[0] as JSONObject).get("meisi_id").toString()
                    val Templateid=(json[0] as JSONObject).get("img").toString().toInt()
                    MyCard1.setImageResource(ImgArray[Templateid-1])
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
    fun getData2(){
        val URL2: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/getData"
        URL2.httpGet(listOf("company_id" to company)).responseJson() { request, response, result2 ->
            when (result2) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json2 = result2.value.obj()
                    val check =json2.get("result")
                    if (check == 1) {
                        Company.text = "企業：" + json2.get("company_name").toString()
                        place.text = json2.get("company_name").toString()
                    }else{
                        Company.text = "取得に失敗しました"
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}