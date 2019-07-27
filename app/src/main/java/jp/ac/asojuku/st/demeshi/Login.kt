package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login : AppCompatActivity() {

    var user_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginBtn.setOnClickListener{Login()}
        RegisterBtn.setOnClickListener{startActivity(Intent(it.context,Register::class.java))}
        ForgotBtn.setOnClickListener{
            Toast.makeText(this, "DEMESHIの次回作にご期待ください", Toast.LENGTH_LONG).show()
        }
    }
    fun Login(){
        val intent = Intent(this,MyCardList::class.java)
        val User_Name = MailText.text.toString()
        val User_Password = PasswordText.text.toString()
        if(!User_Name.isEmpty() and !User_Password.isEmpty()){
            Signal(User_Name,User_Password)
            Handler().postDelayed(Runnable{
                if(user_id != 0){
                    intent.putExtra("UserId", user_id)
                    startActivity(intent)
                }
            },1000)
        }else{
            Toast.makeText(this, "ログイン情報を入力してください", Toast.LENGTH_LONG).show()
        }
    }
    fun Signal(User_Name:String,User_Password:String){
        // 非同期処理
        val user_name = Pair("user_name", User_Name)
        val user_password = Pair("user_password", User_Password)
        val pair = listOf<Pair<String,String>>(user_name,user_password)
        //val URL:String = "http://18001187.pupu.jp/untitled/public/user/login"
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/user/login"
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        user_id = json.get("user_id").toString().toInt()
                        Toast.makeText(this, "ログインしました", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "ログイン情報が間違っています", Toast.LENGTH_LONG).show()
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                    println(result)
                }
            }
        }
    }
}
