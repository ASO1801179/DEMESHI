package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_create_individual.*

class CreateIndividual : AppCompatActivity() {

    var user_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_individual)
        Back.setOnClickListener {
            val intent = Intent(this,Template::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        user_id = intent.getIntExtra("UserId", 0)

        when (intent.getIntExtra("Image", 0)) {
            1 -> {
                BackDesign.setImageResource(new1)
            }
            2 -> {
                BackDesign.setImageResource(new2)
            }
            3 -> {
                BackDesign.setImageResource(new3)
            }
        }
        Name1.bringToFront()
        Phone.bringToFront()
        Mail.bringToFront()
        SNS.bringToFront()
        CreateBtn.setOnClickListener { CardCreate() }
    }
    fun CardCreate() {
        if (!EditName.text.toString().isEmpty() and !EditPhone.text.toString().isEmpty() and !EditMail.text.toString().isEmpty()) {
            val URL: String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/normal_add"
            val name = Pair("name", EditName.text.toString())
            val phone = Pair("number", EditPhone.text.toString())
            val userid = Pair("user_id", Integer.toString(user_id))
            val mailaddress = Pair("address", EditMail.text.toString())
            val sns = Pair("sns", EditSns.text.toString())  //ここに注意
            val img = Pair("img", Integer.toString(intent.getIntExtra("Image", 0)))
            val pair = listOf<Pair<String, String>>(userid, name, phone, mailaddress, sns, img)
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
                        } else {
                            println(request)
                        }
                    }
                    is Result.Failure -> {
                        println("通信に失敗しました。")
                    }
                }
            }
        } else {
            Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_LONG).show()
        }
    }
}