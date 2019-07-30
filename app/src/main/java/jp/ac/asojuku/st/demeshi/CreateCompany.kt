package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_create_company.*
import kotlinx.android.synthetic.main.activity_create_company.Back
import kotlinx.android.synthetic.main.activity_create_company.BackDesign
import kotlinx.android.synthetic.main.activity_create_company.CreateBtn

class CreateCompany : AppCompatActivity() {

    var user_id = 0
    var company_id = ""
    var company_password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_company)
        Back.setOnClickListener{startActivity(Intent(it.context,Template::class.java))}
        CreateBtn.setOnClickListener{Create()}

        user_id = intent.getIntExtra("UserId",0)
        company_id = intent.getStringExtra("CompanyID")
        company_password = intent.getStringExtra("CompanyPass")

        when(intent.getIntExtra("Image",0)){
            7->{
                BackDesign.setImageResource(space)
            }
            8->{
                BackDesign.setImageResource(f4782)
            }
            9->{
                BackDesign.setImageResource(f4792)
            }
        }
        CompanyName.bringToFront()
        Phone.bringToFront()
        Mail.bringToFront()
        URL.bringToFront()
}

    override fun onResume() {
        super.onResume()
        CompanyName.text = "〇〇株式会社"
        Phone.text = "xxx-xxx-xxxx"
        Mail.text = "xxxx@gmial.com"
        URL.text = "会社追加"
    }

    fun Create() {
        if (!EditCompanyName1.text.toString().isEmpty() and !EditPhone.text.toString().isEmpty() and !EditMail.text.toString().isEmpty() and !EditUrl.text.toString().isEmpty() and !place.text.toString().isEmpty()) {
            val URL: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/insert"
            //val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert/" + user_id
            val company_id = Pair("company_id", company_id)
            val company_password = Pair("company_password", company_password)
            val name = Pair("company_name", EditCompanyName1.text.toString())
            val number = Pair("company_phone", EditPhone.text.toString())
            val address = Pair("company_mail", EditMail.text.toString())
            val url = Pair("company_url", EditUrl.text.toString())
            val company_place = Pair("company_place", place.text.toString())
            val img = Pair("img", Integer.toString(intent.getIntExtra("Image", 0)))

            val pair = listOf<Pair<String, String>>(company_id, company_password, name, company_place, number, address, url)
            println(pair)
            Handler().postDelayed(Runnable {

                URL.httpGet(pair).responseJson() { request, response, result ->

                    when (result) {


                        is Result.Success -> {
                            // レスポンスボディを表示
                            val json = result.value.obj()
                            val results = json.get("result")// as JSONArray

                            //作成成功時それ以外は失敗
                            if (results == 1) {
                                val intent = Intent(this, MyCardList::class.java)
                                intent.putExtra("UserId", user_id)
                                Toast.makeText(this, "名刺作成!!", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            } else {
                                println("失敗しました")
                            }
                        }
                        is Result.Failure -> {
                            println(response)
                            println("通信に失敗しました。")
                        }
                    }
                }
            }, 1000)
        }else{
            Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_LONG).show()
        }
    }
}
