package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_company_info.*

class CompanyInfo : AppCompatActivity() {

    var user_id = 0
    var img = 0
    var login = false
    var check1 = false
    var check2 = false
    val regex = Regex("""[a-zA-Z0-9]{9,}""")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_info)
        user_id = intent.getIntExtra("UserId",0)
        img = intent.getIntExtra("Image",0)
        val confirm = intent.getStringExtra("Confirm")
        if(confirm == "Company"){
            CompanyText.text = "企業IDと企業パスワードを設定してください"
            CompanyBtn.text = "登録"
        }else{
            CompanyText.text = "企業IDと企業パスワードでログインしてください"
            CompanyBtn.text = "紐づける"
        }
        CompanyBtn.setOnClickListener{
            if(img <= 6){
                Login()
                Handler().postDelayed(Runnable{
                    if(login) {
                        val intent = Intent(this, CreateCard::class.java)
                        intent.putExtra("UserId", user_id)
                        intent.putExtra("Image", img)
                        intent.putExtra("CompanyID",CompanyID.text.toString())
                        //intent.putExtra("CompanyName",)
                        startActivity(intent)
                    }
                },1000)
            }else{
                check()
                Handler().postDelayed(Runnable{
                    if(check1 and check2) {
                        val intent = Intent(this, CreateCompany::class.java)
                        intent.putExtra("UserId", user_id)
                        intent.putExtra("Image", img)
                        intent.putExtra("CompanyID",CompanyID.text.toString())
                        intent.putExtra("CompanyPass",CompanyPass.text.toString())
                        startActivity(intent)
                    }else{
                        check1 = false
                        check2 = false
                    }
                },1000)
            }
        }
    }
    fun Login(){
        val company_id = Pair("company_id", CompanyID.text.toString())
        val company_password = Pair("company_password", CompanyPass.text.toString())
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/login"
        val pair = listOf<Pair<String,String>>(company_id,company_password)
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 0) {
                        Toast.makeText(this, "企業IDとパスワードが一致しません", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "紐づけました", Toast.LENGTH_LONG).show()
                        login = true
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
    fun check(){
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/company/id_check"
        URL.httpGet(listOf("company_id" to CompanyID.text.toString())).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 1){
                        Toast.makeText(this, "このIDは使用されています", Toast.LENGTH_LONG).show()
                    }else{
                        check1 = true
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
        if(!regex.matches(CompanyPass.text.toString())){
            Toast.makeText(this, "9文字以下です", Toast.LENGTH_LONG).show()
        }else{
            check2 = true
        }
    }
}