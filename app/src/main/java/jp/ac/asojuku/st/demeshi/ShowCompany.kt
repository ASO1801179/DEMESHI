package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_show_company.*
import org.json.JSONObject

class ShowCompany : AppCompatActivity() {

    var user_id = 0
    var card_id = 0
    var company_id = ""
    val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_company)

        user_id = intent.getIntExtra("UserId", 0)
        card_id = intent.getIntExtra("CardId", 0)

        BackBtn.setOnClickListener {
            val sw = intent.getStringExtra("Flag")
            if(sw == "My"){
                val intent = Intent(this, ShowMyCard::class.java)
                intent.putExtra("UserId", user_id)
                intent.putExtra("CardId",card_id)
                startActivity(intent)
            }else{
                val intent = Intent(this, HaveCardDetail::class.java)
                intent.putExtra("UserId", user_id)
                intent.putExtra("CardId",card_id)
                startActivity(intent)
            }
        }
        company_id = intent.getStringExtra("company_id")
        //名刺IDをとって企業名刺の情報表示
        val URL: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/getData?company_id=" + company_id
        URL.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val check = json.get("result").toString()
                    if (check == "1") {
                        CompanyName.text = "企業：" + json.get("company_name").toString()
                        companyName.text = json.get("company_name").toString()
                        Address.text = "所在地：" + json.get("company_place").toString()
                        Place.text = json.get("company_place").toString()
                        PhoneNumber.text = "電話番号：" + json.get("company_phone").toString()
                        Phone.text = json.get("company_phone").toString()
                        MaiAddress.text = "メールアドレス：" + json.get("company_mail").toString()
                        Mail.text = json.get("company_mail").toString()
                        Url_name.text = "URL：" + json.get("company_url").toString()
                        URLName.text = json.get("company_url").toString()
                        val Templateid=json.get("image").toString().toInt()
                        MyCard1.setImageResource(ImgArray[Templateid-1])
                    } else {
                        CompanyName.text = "取得失敗"
                        Address.text = "取得失敗"
                        PhoneNumber.text = "取得失敗"
                        MaiAddress.text = "取得失敗"
                        Url_name.text = "取得失敗"
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
