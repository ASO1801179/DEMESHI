package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    var bool = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        RegisterBtn.setOnClickListener{Register()}
    }

    //ボタン押下
    fun Register(){
        val intent = Intent(this,ConfMail::class.java)
        intent.putExtra("TextFlag","Register")
        val name = CompanyName.text.toString()
        val phone = PhoneNumber.text.toString()
        val mail = Mail.text.toString()
        val pass = Password.text.toString()
        val confpass = ConfPassword.text.toString()
        if(!name.isEmpty() and !phone.isEmpty() and !mail.isEmpty() and !pass.isEmpty() and !confpass.isEmpty()){
            Signal(name,phone,mail,pass)
            Handler().postDelayed(Runnable{
                if(bool){
                    startActivity(intent)
                }
            },1500)
        }else{
            Toast.makeText(this, "全ての項目を入力してください", Toast.LENGTH_LONG).show()
        }
    }

    //登録処理
    fun Signal(name:String,phone:String,mail:String,pass:String){
        // 非同期処理
        val username = Pair("user_name", name)
        val password = Pair("user_password",pass)
        val mailaddress = Pair("mailaddress",mail)
        val number = Pair("phone_number",phone)
        val pair = listOf<Pair<String,String>>(username,password,mailaddress,number)
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/user/insert"
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    when(results){
                        1->{
                            Toast.makeText(this, "メールを送信しました", Toast.LENGTH_LONG).show()
                        }
                        2->{
                            Toast.makeText(this, "メールが既に登録されているものです", Toast.LENGTH_LONG).show()
                            bool = false
                        }
                        3->{
                            Toast.makeText(this, "電話番号が既に登録されているものです", Toast.LENGTH_LONG).show()
                            bool = false
                        }
                        4->{
                            Toast.makeText(this, "メールと電話番号が既に登録されているものです", Toast.LENGTH_LONG).show()
                            bool = false
                        }
                        5->{
                            Toast.makeText(this, "サーバに問題があり、登録できませんでした。", Toast.LENGTH_LONG).show()
                            bool = false
                        }
                        6->{
                            Toast.makeText(this, "入力した値がおかしいです", Toast.LENGTH_LONG).show()
                            bool = false
                        }
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
