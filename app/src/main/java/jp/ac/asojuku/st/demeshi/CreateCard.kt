package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_create_card.*

class CreateCard : AppCompatActivity() {

    var user_id = 0
    var company_name = ""
    var company_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        user_id = intent.getIntExtra("UserId",0)
        company_id = intent.getStringExtra("CompanyID")

        Back.setOnClickListener{
            val intent = Intent(this,Template::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        CreateBtn.setOnClickListener{Create()}
        when(intent.getIntExtra("Image",0)){
            4->{
                    BackDesign.setImageResource(new4)
            }
            5->{
                BackDesign.setImageResource(new5)
            }
            6->{
                BackDesign.setImageResource(new6)
            }
        }
        Name1.bringToFront()
        Phone.bringToFront()
        Mail.bringToFront()
        place.bringToFront()
    }

    override fun onResume() {
        super.onResume()
    }

    fun Create(){
        if(!EditName.getText().toString().isEmpty() and !EditPhone.getText().toString().isEmpty() and !EditMail.getText().toString().isEmpty()) {
            val URL: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/business_add"
            //val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert/" + user_id
            val userid = Pair("user_id", Integer.toString(user_id))
            val name = Pair("name", EditName.text.toString())
            val phone = Pair("number", EditPhone.text.toString())
            val mailaddress = Pair("address", EditMail.text.toString())
            val img = Pair("img", Integer.toString(intent.getIntExtra("Image", 0)))
            val company_id = Pair("company_id", company_id)
            val pair = listOf<Pair<String, String>>(userid, name, phone, mailaddress, img, company_id)
            URL.httpGet(pair).responseJson() { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        // レスポンスボディを表示
                        val json = result.value.obj()
                        val results = json.get("result")// as JSONArray
                        // 作成成功時それ以外は失敗
                        if (results == 1) {
                            val intent = Intent(this, MyCardList::class.java)
                            intent.putExtra("UserId", user_id)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            Toast.makeText(this, "名刺作成!!", Toast.LENGTH_LONG).show()
                            startActivity(intent)
                        }
                    }
                    is Result.Failure -> {
                        println("通信に失敗しました。")
                    }
                }
            }
        }else{
            Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_LONG).show()
        }
    }
}