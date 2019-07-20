package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

//    var name = ""
//    var phone = ""
//    var mail = ""
//    var pass = ""
//    var confpass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        RegisterBtn.setOnClickListener{Register()}
    }

    //ボタン押下
    fun Register(){
        val intent = Intent(this,ConfMail::class.java)
        intent.putExtra("TextFlag","Register")
        val name = Name.text.toString()
        val phone = PhoneNumber.text.toString()
        val mail = Mail.text.toString()
        val pass = Password.text.toString()
        val confpass = ConfPassword.text.toString()
        if(!name.isEmpty() and !phone.isEmpty() and !mail.isEmpty() and !pass.isEmpty() and !confpass.isEmpty()){
            if(Signal(name,phone,mail,pass)){
                startActivity(intent)
            }
//            if(Validate(phone,mail,pass,confpass)){
//                Signal(name,phone,mail,pass)
//                startActivity(intent)
//            }else{
//                Toast.makeText(this, "入力項目が間違っています", Toast.LENGTH_LONG).show()
//            }
        }else{
            Toast.makeText(this, "全ての項目を入力してください", Toast.LENGTH_LONG).show()
        }
    }

    //登録処理
    fun Signal(name:String,phone:String,mail:String,pass:String):Boolean{
        // 非同期処理
        val username = Pair("user_name", name)
        val password = Pair("user_password",pass)
        val mailaddress = Pair("mailaddress",mail)
        val number = Pair("phone_number",phone)
        val pair = listOf<Pair<String,String>>(username,password,mailaddress,number)
        val URL:String = "http://18001187.pupu.jp/untitled/public/user/insert"
        var bool:Boolean = true
        URL.httpGet(pair)
                .responseJson() { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            // レスポンスボディを表示
                            val json = result.value.obj()
                            val results = json.get("result")// as JSONArray
                            when(results){
                                1->{
                                    Toast.makeText(this, "登録できました", Toast.LENGTH_LONG).show()
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
                            }
                        }
                        is Result.Failure -> {
                            println("通信に失敗しました。")
                        }
                    }
                }
        return bool
    }
    //検証
    fun Validate(phone:String,mail:String,password:String,confpass:String):Boolean{
        var result = true
        if(!phone.matches("""^(070|080|090)-\d{4}-\d{4}$""".toRegex())){
            result = false
        }
        if(!mail.matches("""/^[a-zA-Z0-9.!#${'$'}%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*${'$'}/""".toRegex())){
            result = false
        }
        if(!password.equals(confpass)){
            result = false
        }
        if(!password.matches("""^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@[-`{-~])[!-~]{8,48}${'$'}""".toRegex())){
            result = false
        }
        return result
    }
}
