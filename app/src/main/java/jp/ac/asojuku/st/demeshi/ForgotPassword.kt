package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        SendBtn.setOnClickListener{RequestRink()}
    }
    fun RequestRink(){
        val intent = Intent(this,ConfMail::class.java)
        intent.putExtra("TextFlag","Forgot")
        val mailaddress= MailAddress.text.toString()
        val URL:String = "http://18001187.pupu.jp/untitled/public/user/resetmail"
        URL.httpGet(listOf("mailaddress" to mailaddress)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        startActivity(intent)
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
