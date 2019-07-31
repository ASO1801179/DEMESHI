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

    var company_id = ""
    val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_company)
        BackBtn.setOnClickListener { startActivity(Intent(it.context, HaveCardDetail::class.java)) }
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
                        CompanyName.text = json.get("company_name").toString()
                        Address.text = json.get("company_place").toString()
                        PhoneNumber.text = json.get("company_phone").toString()
                        MaiAddress.text = json.get("company_mail").toString()
                        Url_name.text = json.get("company_url").toString()
                        val Templateid=json.get("img").toString().toInt()
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
